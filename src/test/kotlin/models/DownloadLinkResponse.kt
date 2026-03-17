package models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class DownloadLinkResponse(

    @JsonProperty("href")
    val href: String,

    @JsonProperty("method")
    val method: String
)
