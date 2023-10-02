package kt.warmup.musicdb.DTO

data class AlbumCreationRequest(
        val authorHandle: String,
        val name: String,
        // XXX: No Either<A, B> so tracks must be created separately
        val tracks: List<String>,
)
