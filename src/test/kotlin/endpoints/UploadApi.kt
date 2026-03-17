package endpoints

import ApiClient
import io.restassured.response.Response

class UploadApi(private val client: ApiClient) {

    fun getUploadLink(path: String): Response {
        return client.get("/resources/upload?path=$path")
    }
}
