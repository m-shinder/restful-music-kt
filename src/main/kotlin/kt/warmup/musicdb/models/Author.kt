package kt.warmup.musicdb.models

import jakarta.persistence.*

@Entity
@Table(name = "authors")
data class Author (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
        var name: String,
        @Column(unique = true)
        var handle: String,
) {
        @OneToMany(mappedBy = "author")
        var tracks: List<Track> = listOf()
        @OneToMany(mappedBy = "author")
        var albums: List<Album> = listOf()
}