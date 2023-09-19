package kt.warmup.musicdb.repos

import kt.warmup.musicdb.models.APIKey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IAPIkeyRepo: JpaRepository<APIKey, Long> {
}