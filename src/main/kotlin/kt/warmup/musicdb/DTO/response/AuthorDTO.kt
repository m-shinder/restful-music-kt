package kt.warmup.musicdb.DTO.response

data class AuthorDTO(
        val name: String,
        val handle: String,
        val tracks: Collection<String>,
        val albums: Collection<String>,
)
