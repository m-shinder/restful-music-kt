package kt.warmup.musicdb.DTO.request.modify

data class AlbumModifyRequest(
        val authorHandle: String?,
        val name: String?,
        // XXX: No Either<A, B> so tracks must be created separately
        val tracks: List<String>?,
)
