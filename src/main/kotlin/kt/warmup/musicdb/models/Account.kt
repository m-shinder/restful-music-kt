package kt.warmup.musicdb.models

import jakarta.persistence.*
import kt.warmup.musicdb.MusicdbGrantedAuthority
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
        @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
        var associatedAuthors: List<Author> = listOf()

        override fun getAuthorities() = associatedAuthors.map { MusicdbGrantedAuthority.allOver(it.handle) }
                .fold(listOf<MusicdbGrantedAuthority>()) { acc, authorities -> acc + authorities }

        override fun getPassword() = passwordHash

        override fun getUsername() = name

        override fun isAccountNonExpired() = true

        override fun isAccountNonLocked()  = true

        override fun isCredentialsNonExpired() = true

        override fun isEnabled() = true
}
