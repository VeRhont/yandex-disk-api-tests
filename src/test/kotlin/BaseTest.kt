import client.ApiClient
import endpoints.DiskApi
import endpoints.DownloadApi
import endpoints.ResourcesApi
import endpoints.UploadApi
import org.junit.jupiter.api.AfterEach

open class BaseTest {

    private val client = ApiClient()
    private val resourcesToCleanup = mutableListOf<String>()

    protected val diskApi = DiskApi(client)
    protected val resourcesApi = ResourcesApi(client)
    protected val uploadApi = UploadApi(client)
    protected val downloadApi = DownloadApi(client)

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
}
