package kt.warmup.musicdb.DTO.request

data class AuthorizationRequest(
        val email: String,
        val passwordHash: String,
)
