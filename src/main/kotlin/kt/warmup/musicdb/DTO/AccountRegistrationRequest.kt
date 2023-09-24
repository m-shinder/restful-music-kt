package kt.warmup.musicdb.DTO

import kt.warmup.musicdb.models.APIKey

data class AccountRegistrationRequest(
        val apiKey: APIKey,
        val type: String,
        var email: String,
        var passwordHash: String,
        var name: String,
) {
}