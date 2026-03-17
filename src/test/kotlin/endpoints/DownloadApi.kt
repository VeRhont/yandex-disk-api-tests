package endpoints

import ApiClient
import io.restassured.response.Response

class DownloadApi(private val client: ApiClient) {

    fun getDownloadLink(path: String): Response {
        return client.get("/resources/download?path=$path")
    }
}
