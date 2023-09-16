package kt.warmup.musicdb.models

import jakarta.persistence.*
import java.sql.Timestamp
import java.time.Duration

@Entity
@Table(name = "lyric_line")
data class LyricLine(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
        // seems JPA have no Interval type
        val timestamp: Long,
        val duration: Long,
        val line: String,
) {
    @ManyToOne
    lateinit var track: Track
}
