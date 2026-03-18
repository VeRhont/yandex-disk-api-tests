import models.ResourceResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.TestDataFactory

class ResourcesTests : BaseTest() {

    @Test
    fun `should get file metadata`() {
        val path = createRandomFile()

        val resource = getResource(path)

        assertThat(resource.type).isEqualTo("file")
        assertThat(resource.name).isEqualTo(path.substringAfterLast("/"))
        assertThat(resource.size).isGreaterThan(0)
    }

    @Test
    fun `should delete file`() {
        val path = createRandomFile()

        deleteResource(path)

        val response = resourcesApi.getResource(path)
        assertThat(response.statusCode).isEqualTo(404)
    }

    @Test
    fun `should create folder and get its metadata`() {
        val folder = "/${TestDataFactory.getRandomFolderName()}"

        createFolder(folder)
        registerForCleanup(folder)

        val resource = getResource(folder)

        assertThat(resource.name).isEqualTo(folder.substringAfterLast("/"))
        assertThat(resource.path).isEqualTo("disk:$folder")
        assertThat(resource.type).isEqualTo("dir")
    }

    @Test
    fun `should delete folder and verify it no longer exists`() {
        val folder = "/${TestDataFactory.getRandomFolderName()}"

        createFolder(folder)
        deleteResource(folder)

        val response = resourcesApi.getResource(folder)
        assertThat(response.statusCode).isEqualTo(404)
    }

    @Test
    fun `should not create folder if it already exists`() {
        val folder = "/${TestDataFactory.getRandomFolderName()}"

        createFolder(folder)
        registerForCleanup(folder)

        val response = resourcesApi.createFolder(folder)

        assertThat(response.statusCode).isEqualTo(409)
    }

    @Test
    fun `should return 404 for non existing resource`() {
        val path = "/non-existing-${System.currentTimeMillis()}"

        val response = resourcesApi.getResource(path)

        assertThat(response.statusCode).isEqualTo(404)
    }

    private fun createFolder(path: String) {
        val response = resourcesApi.createFolder(path)
        assertThat(response.statusCode).isEqualTo(201)
    }

    private fun getResource(path: String): ResourceResponse {
        val response = resourcesApi.getResource(path)
        assertThat(response.statusCode).isEqualTo(200)
        return response.`as`(ResourceResponse::class.java)
    }

    private fun deleteResource(path: String) {
        val response = resourcesApi.deleteResource(path)
        assertThat(response.statusCode).isIn(202, 204)
    }
}
