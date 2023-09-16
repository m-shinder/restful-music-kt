package kt.warmup.musicdb.models

import jakarta.persistence.*

@Entity
@Table(name = "albums")
data class Album(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
        val name: String,
) {
        @ManyToOne
        lateinit var author: Author
        @ManyToMany
        lateinit var tracks: List<Track>
}
