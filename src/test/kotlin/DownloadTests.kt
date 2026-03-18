import io.restassured.RestAssured
import models.DownloadLinkResponse
import models.UploadLinkResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.FileUtils
import utils.TestDataFactory

class DownloadTests : BaseTest() {

    @Test
    fun `should get download link for existing file`() {
        val fileName = TestDataFactory.getRandomFileName()
        val path = "/$fileName".also {
            registerForCleanup(it)
        }
        val file = FileUtils.createTempFile("test content")

        val uploadLink = uploadApi.getUploadLink(path)
        val href = uploadLink.`as`(UploadLinkResponse::class.java).href

        RestAssured.given().body(file).put(href)

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
