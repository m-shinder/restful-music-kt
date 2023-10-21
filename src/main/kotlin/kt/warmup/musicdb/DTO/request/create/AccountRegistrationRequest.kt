package kt.warmup.musicdb.DTO.request.create

data class AccountRegistrationRequest(
        val type: String,
        var email: String,
        var passwordHash: String,
        var name: String,
) {
}