package config

object TestConfig {

    const val BASE_URL = "https://cloud-api.yandex.net/v1/disk"

    val TOKEN: String = System.getenv("TOKEN")
        ?: throw RuntimeException("TOKEN is not set")
}
