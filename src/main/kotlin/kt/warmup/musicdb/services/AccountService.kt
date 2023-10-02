package kt.warmup.musicdb.services

import kt.warmup.musicdb.DTO.APIKeyDTO
import kt.warmup.musicdb.DTO.AccountRegistrationRequest
import kt.warmup.musicdb.DTO.AuthorizationRequest
import kt.warmup.musicdb.models.Account
import kt.warmup.musicdb.repos.IAccountRepo
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AccountService(
        val repository: IAccountRepo,
): UserDetailsService {

    override fun loadUserByUsername(username: String) = repository.findByEmail(username)

    fun register(request: AccountRegistrationRequest): Account {
        if (repository.existsByEmail(request.email))
            throw Exception()
        val account = Account(
            type = request.type,
            email = request.email,
            passwordHash = request.passwordHash,
            hashSalt = "NO_SALT",
            name = request.name,
        )
        repository.save(account)
        return account
    }

    internal fun authorize(request: AuthorizationRequest): Account {
        // TODO: actual authorization
        val account = repository.findByEmail(request.email)
        if (account.passwordHash == request.passwordHash)
            return account
        else
            throw Exception()
    }
}