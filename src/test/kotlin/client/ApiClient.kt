package client

import config.TestConfig
import io.restassured.RestAssured
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification

class ApiClient {

    init {
        RestAssured.baseURI = TestConfig.BASE_URL
    }

    private fun baseRequest(): RequestSpecification =
        RestAssured
            .given()
            .header("Authorization", "OAuth ${TestConfig.TOKEN}")
            .log().all()

    fun get(path: String): Response =
        baseRequest().get(path).log()

    fun delete(path: String): Response =
        baseRequest().delete(path).log()

    fun post(path: String, body: Any? = null): Response {
        val request = baseRequest()
        if (body != null) request.body(body)
        return request.post(path).log()
    }

    fun put(path: String, body: Any? = null): Response {
        val request = baseRequest()
        if (body != null) request.body(body)
        return request.put(path).log()
    }

    fun patch(path: String, body: Any? = null): Response {
        val request = baseRequest()
        if (body != null) request.body(body)
        return request.patch(path).log()
    }

    private fun Response.log() = this.also {
        this.then().log().all()
    }
}
