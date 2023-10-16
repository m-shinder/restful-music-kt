package kt.warmup.musicdb.services

import io.jsonwebtoken.Jwts
import kt.warmup.musicdb.models.APIKeyType
import kt.warmup.musicdb.models.Account
import kt.warmup.musicdb.repos.IAPIkeyRepo
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.Duration
import java.time.Instant

@Service
class APIKeyService(
        val repository: IAPIkeyRepo,
) {
    private val jwtSignKey = Jwts.SIG.HS512.key().build()
    fun issueAnonymous(): String {
        return issue(
                Account(0,"", "", "", "", ""),
                APIKeyType.ANONYMOUS,
        )
    }

    fun issueCreator(creator: Account): String {
        return issue(
                creator,
                APIKeyType.CREATOR,
        )
    }

    private fun issue(issuer: Account, type: APIKeyType): String {
        val iat = Timestamp.from(Instant.now())
        val eat = Timestamp.from(Instant.now().plus(Duration.ofDays(1)))
        val jwt = Jwts.builder()
                .issuedAt(iat)
                .expiration(eat)
                .issuer(issuer.name)
                .signWith(jwtSignKey)
                .compact()
        return jwt
    }
}