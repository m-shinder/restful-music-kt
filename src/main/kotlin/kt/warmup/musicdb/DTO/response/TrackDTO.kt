package kt.warmup.musicdb.DTO.response

import kt.warmup.musicdb.DTO.response.LyricLineDTO

data class TrackDTO(
        val ISMN: String,
        val name: String,
        val filehash: String,
        val authorName: String,
        val authorURL: String,
        val albums: Collection<String>,
        val lyric: List<LyricLineDTO>?
)
