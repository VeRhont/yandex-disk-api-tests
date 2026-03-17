package models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ErrorResponse(

    @JsonProperty("message")
    val message: String? = null,

    @JsonProperty("description")
    val description: String? = null,

    @JsonProperty("error")
    val error: String? = null
)
