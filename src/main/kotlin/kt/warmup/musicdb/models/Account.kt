package kt.warmup.musicdb.models

import jakarta.persistence.*

@Entity
@Table(name = "accounts")
data class Account(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
        val type: String,
        var email: String,
        var passwordHash: String,
        var name: String,
) {

}
