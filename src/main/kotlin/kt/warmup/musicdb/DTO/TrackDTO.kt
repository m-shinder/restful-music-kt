package kt.warmup.musicdb.DTO

import kt.warmup.musicdb.models.Album
import kt.warmup.musicdb.models.Track

data class TrackDTO(
        val ISMN: String,
        val name: String,
        val filehash: String,
        val authorName: String,
        val authorURL: String,
        val albums: Collection<String>,
        val lyric: List<LyricLineDTO>?
)
