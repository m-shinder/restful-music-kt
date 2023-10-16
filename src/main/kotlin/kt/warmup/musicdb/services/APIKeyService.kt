package kt.warmup.musicdb.services

import io.jsonwebtoken.Jwts
import kt.warmup.musicdb.models.Account
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.Duration
import java.time.Instant

@Service
class APIKeyService {
    private val jwtSignKey = Jwts.SIG.HS512.key().build()
    private val jwtParser = Jwts.parser().verifyWith(jwtSignKey).build()

    fun issueAnonymous(): String {
        return issue(
                Account(0,"", "", "", "", ""),
        )
    }

    fun issueCreator(creator: Account): String {
        return issue(
                creator,
        )
    }

    fun issuerOf(token: String) = jwtParser.parseSignedClaims(token).payload.issuer

    private fun issue(issuer: Account): String {
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