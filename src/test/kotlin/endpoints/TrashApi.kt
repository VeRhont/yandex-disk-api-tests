package endpoints

import client.ApiClient
import io.restassured.response.Response

class TrashApi(private val client: ApiClient) {

    fun deleteTrash(path: String? = null): Response {
        val query = if (path != null) "path=$path" else ""
        return client.delete("/trash/resources$query")
    }

    fun getTrash(): Response {
        return client.get("/trash/resources")
    }

    fun restoreResource(path: String): Response {
        return client.put("/trash/resources/restore?path=$path")
    }
}
