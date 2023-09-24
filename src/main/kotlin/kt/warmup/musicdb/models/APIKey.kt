package kt.warmup.musicdb.models

import jakarta.persistence.*
import java.sql.Timestamp

enum class APIKeyType {
        ANONYMOUS,
        AUTHOR,
        USER,
        CREATOR,
}
@Entity
@Table(name = "api_keys")
data class APIKey(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
        @Column(name = "api_key_value")
        val value: String,
        @ManyToOne
        val issuer: Account,
        val type: APIKeyType,
        val timeToUse: Long,
        val validFrom: Timestamp,
        val validTo: Timestamp,
)
