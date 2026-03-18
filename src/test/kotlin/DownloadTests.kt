import models.DownloadLinkResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DownloadTests : BaseTest() {

    @Test
    fun `should get download link for existing file`() {
        val path = createRandomFile()

        val response = downloadApi.getDownloadLink(path)
        assertThat(response.statusCode).isEqualTo(200)

        val link = response.`as`(DownloadLinkResponse::class.java)
        assertThat(link.href).isNotBlank
    }

    @Test
    fun `should return error when downloading non existing file`() {
        val path = "/non-existing-${System.currentTimeMillis()}"

        val response = downloadApi.getDownloadLink(path)

        assertThat(response.statusCode).isEqualTo(404)
    }
}
