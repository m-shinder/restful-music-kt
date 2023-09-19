package kt.warmup.musicdb.DTO

data class AuthorRegistrationRequest(
        val accountKey: String,
        val name: String,
        val handle: String,
)
