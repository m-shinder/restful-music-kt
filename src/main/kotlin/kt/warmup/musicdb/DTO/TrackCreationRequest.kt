package kt.warmup.musicdb.DTO

data class TrackCreationRequest(
        val authorHandle: String,
        val name: String,
        val filehash: String,
        val ISMN: String?,
        val albums: Collection<String>?,
        val lyric: List<LyricLineDTO>?
)
