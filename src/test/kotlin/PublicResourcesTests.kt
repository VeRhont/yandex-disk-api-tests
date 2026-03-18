import models.DownloadLinkResponse
import models.ResourceResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PublicResourcesTests : BaseTest() {

    @Test
    fun `should publish file and get public url`() {
        val path = createRandomFile()

        val publishResponse = publicApi.publish(path)
        assertThat(publishResponse.statusCode).isIn(200, 202)

        val resource = waitUntilPublished(path)

        assertThat(resource.publicKey).isNotNull
    }

    @Test
    fun `should get public metadata`() {
        val path = createRandomFile()

        publicApi.publish(path)

        val resource = waitUntilPublished(path)

        val publicKey = resource.publicKey
        assertThat(publicKey).isNotNull()

        val response = publicApi.getPublicResource(publicKey!!)
        assertThat(response.statusCode).isEqualTo(200)

        val publicResource = response.`as`(ResourceResponse::class.java)
        assertThat(publicResource.name).isEqualTo(path.substringAfterLast("/"))
    }

    @Test
    fun `should download public file`() {
        val path = createRandomFile()

        publicApi.publish(path)

        val resource = waitUntilPublished(path)

        val publicKey = resource.publicKey
        assertThat(publicKey).isNotNull()

        val downloadResponse = publicApi.getDownloadLink(publicKey!!)
        assertThat(downloadResponse.statusCode).isEqualTo(200)

        val link = downloadResponse.`as`(DownloadLinkResponse::class.java)
        assertThat(link.href).isNotBlank
    }

    @Test
    fun `should save public file to disk`() {
        val path = createRandomFile()

        publicApi.publish(path)

        val resource = waitUntilPublished(path)

        val publicKey = resource.publicKey
        assertThat(publicKey).isNotNull()

        val saveResponse = publicApi.saveToDisk(publicKey!!)
        assertThat(saveResponse.statusCode).isIn(201, 202)
    }

    @Test
    fun `should return 404 when publishing non existing file`() {
        val path = "/non-existing-${System.currentTimeMillis()}"

        val response = publicApi.publish(path)

        assertThat(response.statusCode).isEqualTo(404)
    }

    @Test
    fun `should return 404 for invalid public key`() {
        val invalidKey = "invalid-key-${System.currentTimeMillis()}"

        val response = publicApi.getPublicResource(invalidKey)

        assertThat(response.statusCode).isEqualTo(404)
    }

    @Test
    fun `should return 404 when downloading with invalid public key`() {
        val invalidKey = "invalid-key-${System.currentTimeMillis()}"

        val response = publicApi.getDownloadLink(invalidKey)

        assertThat(response.statusCode).isEqualTo(404)
    }

    private fun waitUntilPublished(path: String): ResourceResponse {
        repeat(30) {
            val resource = resourcesApi.getResource(path)
                .`as`(ResourceResponse::class.java)

            if (resource.publicKey != null) {
                return resource
            }
            Thread.sleep(1000L)
        }
        throw AssertionError("Resource was not published in time")
    }
}
