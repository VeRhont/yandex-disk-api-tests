package endpoints

import client.ApiClient
import io.restassured.response.Response

class ResourcesApi(private val client: ApiClient) {

    fun getResource(path: String): Response {
        return client.get("$BASE_URL?path=$path")
    }

    fun createFolder(path: String): Response {
        return client.put("$BASE_URL?path=$path")
    }

    fun deleteResource(path: String): Response {
        return client.delete("$BASE_URL?path=$path")
    }

    fun moveResource(from: String, to: String): Response {
        return client.post("$BASE_URL/move?from=$from&path=$to")
    }

    private companion object {
        private const val BASE_URL = "/resources"
    }
}
