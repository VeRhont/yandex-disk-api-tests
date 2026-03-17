package models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class EmbeddedResource(

    @JsonProperty("items")
    val items: List<ResourceItem> = emptyList(),

    @JsonProperty("total")
    val total: Int? = null
)
