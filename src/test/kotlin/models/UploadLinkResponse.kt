package models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class UploadLinkResponse(

    @JsonProperty("href")
    val href: String,

    @JsonProperty("method")
    val method: String,

    @JsonProperty("templated")
    val templated: Boolean
)
