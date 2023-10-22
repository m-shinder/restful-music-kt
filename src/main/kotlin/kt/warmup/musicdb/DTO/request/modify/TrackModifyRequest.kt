package kt.warmup.musicdb.DTO.request.modify

import kt.warmup.musicdb.DTO.response.LyricLineDTO

data class TrackModifyRequest(
        val name: String?,
        val filehash: String?,
        val lyric: List<LyricLineDTO>?
)
