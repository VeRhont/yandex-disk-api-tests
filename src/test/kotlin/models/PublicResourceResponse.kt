package models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class PublicResourceResponse(

    @JsonProperty("public_url")
    val publicUrl: String? = null
)
