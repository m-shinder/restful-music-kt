package kt.warmup.musicdb.repos

import kt.warmup.musicdb.models.Track
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ITrackRepo: JpaRepository<Track, Long> {
}