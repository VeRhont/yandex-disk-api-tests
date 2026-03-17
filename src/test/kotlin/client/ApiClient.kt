import config.TestConfig
import io.restassured.RestAssured
import io.restassured.response.Response

class ApiClient {

    init {
        RestAssured.baseURI = TestConfig.BASE_URL
    }

    private fun request() =
        RestAssured
            .given()
            .header("Authorization", "OAuth ${TestConfig.TOKEN}")
            .log().all()

    fun get(path: String): Response =
        request()
            .get(path)
            .then()
            .log().all()
            .extract()
            .response()

    fun post(path: String): Response =
        request()
            .post(path)
            .then()
            .log().all()
            .extract()
            .response()

    fun put(path: String): Response =
        request()
            .put(path)
            .then()
            .log().all()
            .extract()
            .response()

    fun delete(path: String): Response =
        request()
            .delete(path)
            .then()
            .log().all()
            .extract()
            .response()

    fun patch(path: String): Response =
        request()
            .patch(path)
            .then()
            .log().all()
            .extract()
            .response()
}
