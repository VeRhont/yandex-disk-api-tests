package models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class DiskInfoResponse(

    @JsonProperty("total_space")
    val totalSpace: Long,

    @JsonProperty("used_space")
    val usedSpace: Long,

    @JsonProperty("trash_size")
    val trashSize: Long,

    @JsonProperty("is_paid")
    val isPaid: Boolean? = null,

    @JsonProperty("revision")
    val revision: Long? = null
)
