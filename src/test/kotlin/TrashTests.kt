import io.restassured.RestAssured
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TrashTests : BaseTest() {

    @BeforeEach
    fun cleanTrash() {
        trashApi.deleteTrash()
    }

    @Test
    fun `should move file to trash after delete`() {
        val path = createRandomFile()
        resourcesApi.deleteResource(path)

        val response = trashApi.getTrash()

        assertThat(response.statusCode).isEqualTo(200)
        assertThat(response.asString()).contains(path.substringAfterLast("/"))
    }

    @Test
    fun `should clear trash`() {
        val path = createRandomFile()
        val fileName = path.substringAfterLast("/")

        val deleteResponse = resourcesApi.deleteResource(path)
        assertThat(deleteResponse.statusCode).isIn(202, 204)

        waitForFileInTrash(fileName)

        val clearResponse = trashApi.deleteTrash()
        assertThat(clearResponse.statusCode).isIn(202, 204)

        waitForFileNotInTrash(fileName)

        val response = trashApi.getTrash()
        val items = response.jsonPath().getList<String>("_embedded.items.name") ?: emptyList()

        assertThat(items).doesNotContain(fileName)
    }

    @Test
    fun `should restore file from trash`() {
        val path = createRandomFile()
        val fileName = path.substringAfterLast("/")

        resourcesApi.deleteResource(path)

        waitForFileInTrash(fileName)

        val trashResponse = trashApi.getTrash()

        val items = trashResponse.jsonPath().getList<Map<String, Any>>("_embedded.items")

        val trashItem = items.first { it["name"] == fileName }
        val trashPath = trashItem["path"] as String

        val restoreResponse = trashApi.restoreResource(trashPath)

        assertThat(restoreResponse.statusCode).isIn(201, 202)

        if (restoreResponse.statusCode == 202) {
            val operationUrl = restoreResponse.jsonPath().getString("href")
            waitForOperation(operationUrl)
        }

        val getResponse = resourcesApi.getResource(path)
        assertThat(getResponse.statusCode).isEqualTo(200)
    }

    @Test
    fun `should return 404 when restoring non existing trash resource`() {
        val fakePath = "trash:/non-existing-file"

        val response = trashApi.restoreResource(fakePath)

        assertThat(response.statusCode).isEqualTo(404)
    }

    @Test
    fun `should return 404 when deleting non existing trash resource`() {
        val fakePath = "trash:/non-existing-file"

        val response = trashApi.deleteTrash(fakePath)

        assertThat(response.statusCode).isEqualTo(404)
    }

    private fun waitForFileStatus(
        fileName: String,
        shouldExist: Boolean,
        retries: Int = 15
    ) {
        repeat(retries) {
            val items = trashApi.getTrash().jsonPath().getList<String>("_embedded.items.name") ?: emptyList()
            val exists = items.contains(fileName)
            if (exists == shouldExist) return

            Thread.sleep(1000)
        }

        val errorMsg =
            if (shouldExist) "File not found in trash: $fileName" else "File still exists in trash: $fileName"
        throw AssertionError(errorMsg)
    }

    private fun waitForFileInTrash(fileName: String) =
        waitForFileStatus(fileName, true)

    private fun waitForFileNotInTrash(fileName: String) =
        waitForFileStatus(fileName, false)

    private fun waitForOperation(operationUrl: String) {
        repeat(30) {
            val response = RestAssured.get(operationUrl)
            val status = response.jsonPath().getString("status")
            if (status == "success") return

            Thread.sleep(1000)
        }
        throw AssertionError("Operation not completed in time")
    }
}
