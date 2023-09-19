package kt.warmup.musicdb.repos

import kt.warmup.musicdb.models.LyricLine
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ILyricLineRepo : JpaRepository<LyricLine, Long> {
}