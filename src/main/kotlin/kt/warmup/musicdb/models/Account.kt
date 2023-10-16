package kt.warmup.musicdb.models

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "accounts")
data class Account (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
        val type: String,
        var email: String,
        var passwordHash: String,
        var hashSalt: String,
        var name: String,
): UserDetails {
        @OneToMany(mappedBy = "account")
        var associatedAuthors: List<Author> = listOf()

        override fun getAuthorities() = mutableListOf<GrantedAuthority>()

        override fun getPassword() = passwordHash

        override fun getUsername() = name

        override fun isAccountNonExpired() = true

        override fun isAccountNonLocked()  = true

        override fun isCredentialsNonExpired() = true

        override fun isEnabled() = true
}
