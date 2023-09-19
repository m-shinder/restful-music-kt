package kt.warmup.musicdb.services

import kt.warmup.musicdb.DTO.TrackCreationRequest
import kt.warmup.musicdb.DTO.TrackDTO
import kt.warmup.musicdb.models.Author
import kt.warmup.musicdb.models.Track
import kt.warmup.musicdb.repos.ITrackRepo
import org.springframework.data.domain.Example
import org.springframework.stereotype.Service

const val BASE_AUTHOR_URL = "/api/v1/author/"
@Service
class TrackService(
        private var repository: ITrackRepo,
        private var authors: AuthorService

) {
    fun getAll(): Collection<TrackDTO> = repository.findAll().map{
        it.toDTO()
    }

    fun getByISMN(ismn: String): TrackDTO = modelByISMN(ismn).toDTO()

    fun getByFilehash(hash: String): TrackDTO = modelByFilehash(hash).toDTO()

    fun create(track: TrackCreationRequest): TrackDTO {
        val model = Track(
                ISMN = track.ISMN ?: "",
                name = track.name,
                filehash = track.filehash,
        )
        model.author = authors.modelByHandle(track.creatorKey)
        repository.save(model)
        return model.toDTO()
    }

    fun updateByFilehash(hash: String, track: TrackDTO): TrackDTO {
        val old = modelByFilehash(hash)
        old.name = track.name
        repository.save(old)
        return old.toDTO()
    }

    fun deleteByHash(hash: String) = repository.deleteById(modelByFilehash(hash).id)

    internal fun updateById(id: Long, track: TrackDTO): TrackDTO {
        val old = repository.getReferenceById(id)
        old.name = track.name
        repository.save(old)
        return old.toDTO()
    }

    internal fun modelByFilehash(hash: String): Track {
        val model = repository.findOne(Example.of(Track(
                ISMN = "",
                name = "",
                filehash = hash,
        ))).get()
        return model
    }

    internal fun modelByISMN(ismn: String): Track {
        val model = repository.findOne(Example.of(Track(
                ISMN = ismn,
                name = "",
                filehash = "",
        ))).get()
        return model
    }

    internal fun getById(id: Long): TrackDTO = repository.getReferenceById(id).toDTO()

    internal fun deleteById(id: Long)  = repository.deleteById(id)
}

fun Track.toDTO(): TrackDTO {
        return TrackDTO(
                ISMN = this.ISMN,
                name = this.name,
                filehash = this.filehash,
                authorName = this.author.name,
                authorURL = BASE_AUTHOR_URL + this.author.handle,
                albums = this.albums.map { it.name },
                lyric = null,
    )
}