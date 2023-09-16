package kt.warmup.musicdb.models

import jakarta.persistence.*

@Entity
@Table(name = "tracks")
data class Track (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
        val ISMN: String,
        var name: String,
        var filehash: String,
) {
        @ManyToOne
        lateinit var author: Author
        @ManyToMany
        var albums: List<Album> = listOf()
        @OneToMany(mappedBy = "track")
        var lyrics: List<LyricLine> = listOf()
}