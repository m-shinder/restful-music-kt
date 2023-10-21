package kt.warmup.musicdb.DTO.response

import java.sql.Timestamp

data class APIKeyDTO(
        val type: String,
        val value: String,
        val validTo: Timestamp,
)
