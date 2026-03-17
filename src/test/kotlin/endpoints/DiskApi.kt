package endpoints

import ApiClient
import io.restassured.response.Response

class DiskApi(private val client: ApiClient) {

    fun getDiskInfo(): Response {
        return client.get("/")
    }
}
