package kt.warmup.musicdb.DTO.request.create

import kt.warmup.musicdb.DTO.response.LyricLineDTO

data class TrackCreationRequest(
        val authorHandle: String,
        val name: String,
        val filehash: String,
        val ISMN: String?,
        val lyric: List<LyricLineDTO>?
)
