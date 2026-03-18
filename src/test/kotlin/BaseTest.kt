import client.ApiClient
import endpoints.*
import io.restassured.RestAssured
import models.UploadLinkResponse
import org.junit.jupiter.api.AfterEach
import utils.FileUtils
import utils.TestDataFactory

open class BaseTest {

    private val client = ApiClient()
    private val resourcesToCleanup = mutableListOf<String>()

    protected val diskApi = DiskApi(client)
    protected val resourcesApi = ResourcesApi(client)
    protected val uploadApi = UploadApi(client)
    protected val downloadApi = DownloadApi(client)
    protected val trashApi = TrashApi(client)

    protected fun registerForCleanup(path: String) {
        resourcesToCleanup.add(path)
    }

    @AfterEach
    fun cleanup() {
        resourcesToCleanup.forEach {
            try {
                resourcesApi.deleteResource(it)
            } catch (e: Exception) {
                println("Failed to cleanup resource: $it")
            }
        }
        resourcesToCleanup.clear()
    }

    private fun createFile(path: String): String {
        val file = FileUtils.createTempFile("test content")

        val uploadLink = uploadApi.getUploadLink(path)
            .`as`(UploadLinkResponse::class.java)

        RestAssured
            .given()
            .body(file)
            .put(uploadLink.href)

        registerForCleanup(path)

        return path
    }

    protected fun createRandomFile(): String {
        val path = "/${TestDataFactory.getRandomFileName()}"
        return createFile(path)
    }
}
