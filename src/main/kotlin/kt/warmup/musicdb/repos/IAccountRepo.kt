package kt.warmup.musicdb.repos

import kt.warmup.musicdb.models.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IAccountRepo: JpaRepository<Account, Long> {
    fun findByEmail(email: String): Account
    fun findByName(email: String): Account

    fun existsByEmail(email: String): Boolean
    fun existsByName(email: String): Boolean

}