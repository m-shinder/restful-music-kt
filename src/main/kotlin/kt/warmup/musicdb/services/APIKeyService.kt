package kt.warmup.musicdb.services

import io.jsonwebtoken.Jwts
import kt.warmup.musicdb.DTO.APIKeyDTO
import kt.warmup.musicdb.models.APIKey
import kt.warmup.musicdb.models.APIKeyType
import kt.warmup.musicdb.models.Account
import kt.warmup.musicdb.models.Author
import kt.warmup.musicdb.repos.IAPIkeyRepo
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.security.Key
import java.sql.Timestamp
import java.time.Duration
import java.time.Instant

@Service
class APIKeyService(
        val repository: IAPIkeyRepo,
) {
    private val jwtSignKey = Jwts.SIG.HS512.key().build()
    fun issueAnonymous(): APIKeyDTO {
        return issue(
                Account(0,"", "", "", "", ""),
                APIKeyType.ANONYMOUS,
                10
        ).toDTO()
    }

    fun issueCreator(creator: Account): APIKeyDTO {
        return issue(
                creator,
                APIKeyType.CREATOR,
                10
        ).toDTO()
    }

    fun validate(value: String): Boolean {
        val key = repository.findByValue(value)
        val valid = isValid(key)
        if (!valid) disposeByValue(value)
        return valid
    }

    fun disposeByValue(value: String) = repository.deleteById(
            getModelByValue(value).id
    )

    internal fun getIssuerByValue(value: String) = repository.findByValue(value).issuer

    internal fun getModelByValue(value: String): APIKey {
        val model = repository.findByValue(value)
        return  model
    }

    internal fun isValid(key: APIKey) = !isOutdated(key)

    internal fun isOutdated(key: APIKey) = key.timeToUse <= 0
                       || key.validFrom > Timestamp.from(Instant.now())
                       || key.validFrom < Timestamp.from(Instant.now())

    private fun typeOfAPIKey(key: String): APIKeyType {
        val type = key.substringBefore(':')
        return APIKeyType.valueOf(type)
    }

    private fun issue(issuer: Account, type: APIKeyType, timeToUse: Long): APIKey {
        val iat = Timestamp.from(Instant.now())
        val eat = Timestamp.from(Instant.now().plus(Duration.ofDays(1)))
        val jwt = Jwts.builder()
                .issuedAt(iat)
                .expiration(eat)
                .issuer(issuer.name)
                .signWith(jwtSignKey)
                .compact()

        val key = APIKey(
                value = jwt,
                issuer = issuer,
                type = type,
                timeToUse = timeToUse,
                validFrom = iat,
                validTo = eat
        )
        repository.save(key)
        return key
    }
}

fun APIKey.toDTO() = APIKeyDTO(
        type = this.type.toString(),
        value = this.value,
        validTo = this.validTo,
)