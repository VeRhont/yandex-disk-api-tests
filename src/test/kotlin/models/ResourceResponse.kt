package models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ResourceResponse(

    @JsonProperty("name")
    val name: String,

    @JsonProperty("path")
    val path: String,

    @JsonProperty("type")
    val type: String,

    @JsonProperty("public_key")
    val publicKey: String? = null,

    @JsonProperty("public_url")
    val publicUrl: String? = null,

    @JsonProperty("created")
    val created: String? = null,

    @JsonProperty("modified")
    val modified: String? = null,

    @JsonProperty("size")
    val size: Long? = null,

    @JsonProperty("mime_type")
    val mimeType: String? = null,

    @JsonProperty("_embedded")
    val embedded: EmbeddedResource? = null,
)
