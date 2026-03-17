package endpoints

import ApiClient
import io.restassured.response.Response

class ResourcesApi(private val client: ApiClient) {

    fun getResource(path: String): Response {
        return client.get("/resources?path=$path")
    }

    fun createFolder(path: String): Response {
        return client.put("/resources?path=$path")
    }

    fun deleteResource(path: String): Response {
        return client.delete("/resources?path=$path")
    }

    fun moveResource(from: String, to: String): Response {
        return client.post("/resources/move?from=$from&path=$to")
    }
}
