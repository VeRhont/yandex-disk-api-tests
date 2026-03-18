import io.restassured.RestAssured
import models.ResourceResponse
import models.UploadLinkResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.FileUtils
import utils.TestDataFactory
import java.io.File

class UploadTests : BaseTest() {

    @Test
    fun `should get upload link`() {
        val path = "/${TestDataFactory.getRandomFileName()}"

        val link = getUploadLink(path)

        assertThat(link.href).isNotBlank
        assertThat(link.method).isEqualTo("PUT")
    }

    @Test
    fun `should upload file and verify it exists`() {
        val fileName = TestDataFactory.getRandomFileName()
        val path = "/$fileName"

        val file = FileUtils.createTempFile("Hello Yandex!")

        registerForCleanup(path)

        val link = getUploadLink(path)

        uploadFile(file, link.href)

        val resource = getResource(path)

        assertThat(resource.name).isEqualTo(fileName)
        assertThat(resource.type).isEqualTo("file")
        assertThat(resource.size).isGreaterThan(0)
    }

    @Test
    fun `should upload file into folder`() {
        val folder = "/${TestDataFactory.getRandomFolderName()}"
        val fileName = TestDataFactory.getRandomFileName()
        val path = "$folder/$fileName"

        resourcesApi.createFolder(folder)
        registerForCleanup(folder)

        val file = FileUtils.createTempFile("file content")
        val link = getUploadLink(path)

        uploadFile(file, link.href)

        val resource = getResource(path)

        assertThat(resource.path).isEqualTo("disk:$path")
        assertThat(resource.type).isEqualTo("file")
    }

    @Test
    fun `should overwrite existing file when uploading again`() {
        val fileName = TestDataFactory.getRandomFileName()
        val path = "/$fileName"

        val file1 = FileUtils.createTempFile("first version")
        val file2 = FileUtils.createTempFile("second version")

        registerForCleanup(path)

        val link1 = getUploadLink(path, overwrite = true)
        uploadFile(file1, link1.href)

        val link2 = getUploadLink(path, overwrite = true)
        uploadFile(file2, link2.href)

        val resource = getResource(path)

        assertThat(resource.name).isEqualTo(fileName)
        assertThat(resource.type).isEqualTo("file")
        assertThat(resource.size).isGreaterThan(0)
    }

    @Test
    fun `should not overwrite file when overwrite is false`() {
        val fileName = TestDataFactory.getRandomFileName()
        val path = "/$fileName"

        val file1 = FileUtils.createTempFile("first version")

        registerForCleanup(path)

        val link1 = getUploadLink(path, overwrite = true)
        uploadFile(file1, link1.href)

        val response = uploadApi.getUploadLink(path, overwrite = false)
        assertThat(response.statusCode).isEqualTo(409)
    }

    private fun getUploadLink(path: String, overwrite: Boolean = false): UploadLinkResponse {
        val response = uploadApi.getUploadLink(path, overwrite)
        assertThat(response.statusCode).isEqualTo(200)
        return response.`as`(UploadLinkResponse::class.java)
    }

    private fun uploadFile(file: File, href: String) {
        val response = RestAssured
            .given()
            .body(file)
            .put(href)

        assertThat(response.statusCode).isIn(201, 202)
    }

    private fun getResource(path: String): ResourceResponse {
        val response = resourcesApi.getResource(path)
        assertThat(response.statusCode).isEqualTo(200)
        return response.`as`(ResourceResponse::class.java)
    }
}
