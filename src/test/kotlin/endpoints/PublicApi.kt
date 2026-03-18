package endpoints

import client.ApiClient

class PublicApi(private val client: ApiClient) {

    fun publish(path: String) =
        client.put("/resources/publish?path=$path")

    fun unpublish(path: String) =
        client.put("/resources/unpublish?path=$path")

    fun getPublicResource(publicKey: String) =
        client.get("/public/resources?public_key=$publicKey")

    fun getDownloadLink(publicKey: String) =
        client.get("/public/resources/download?public_key=$publicKey")

    fun saveToDisk(publicKey: String) =
        client.post("/public/resources/save-to-disk?public_key=$publicKey")
}
