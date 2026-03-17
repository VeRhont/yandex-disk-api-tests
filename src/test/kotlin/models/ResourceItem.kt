package models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ResourceItem(

    @JsonProperty("name")
    val name: String,

    @JsonProperty("path")
    val path: String,

    @JsonProperty("type")
    val type: String,

    @JsonProperty("size")
    val size: Long? = null
)
