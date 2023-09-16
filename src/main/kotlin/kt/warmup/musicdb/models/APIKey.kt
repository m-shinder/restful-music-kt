package kt.warmup.musicdb.models

import jakarta.persistence.*
import java.sql.Timestamp

@Entity
@Table(name = "api_keys")
data class APIKey(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
        val value: String,
        val issuer: Account,
        val type: String,
        val timeToUse: Long,
        val validFrom: Timestamp,
        val validTo: Timestamp,
)
