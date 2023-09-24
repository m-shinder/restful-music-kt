package kt.warmup.musicdb.DTO

data class AuthorizationRequest(
        val email: String,
        val passwordHash: String,
)
