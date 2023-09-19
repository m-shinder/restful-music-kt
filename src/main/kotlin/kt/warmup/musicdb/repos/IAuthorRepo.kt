package kt.warmup.musicdb.repos

import kt.warmup.musicdb.models.Author
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IAuthorRepo : JpaRepository<Author, Long> {
}