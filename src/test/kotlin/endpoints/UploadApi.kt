package endpoints

import client.ApiClient
import io.restassured.response.Response

class UploadApi(private val client: ApiClient) {

    fun getUploadLink(path: String, overwrite: Boolean = false): Response {
        return client.get("/resources/upload?path=$path&overwrite=$overwrite")
    }
}
