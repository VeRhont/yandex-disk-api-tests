import endpoints.DiskApi
import endpoints.DownloadApi
import endpoints.ResourcesApi
import endpoints.UploadApi

open class BaseTest {
    private val client = ApiClient()

    protected val diskApi = DiskApi(client)
    protected val resourcesApi = ResourcesApi(client)
    protected val uploadApi = UploadApi(client)
    protected val downloadApi = DownloadApi(client)
}
