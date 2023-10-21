package kt.warmup.musicdb.services

import kt.warmup.musicdb.DTO.AlbumCreationRequest
import kt.warmup.musicdb.DTO.response.AlbumDTO
import kt.warmup.musicdb.models.Album
import kt.warmup.musicdb.repos.IAlbumRepo
import org.springframework.stereotype.Service

@Service
class AlbumService(
        val repository: IAlbumRepo,
        val tracks: TrackService,
        val authors: AuthorService
) {
    fun getAll(): Collection<AlbumDTO> = repository.findAll().map {
        it.toDTO()
    }

    fun getByAuthorHandle(handle: String): Collection<AlbumDTO> {
        return repository.findByAuthor(authors.modelByHandle(handle)).map { it.toDTO() }
    }

    fun getByAuthorAndName(handle: String, name: String): AlbumDTO {
        return repository.findByAuthorAndName(authors.modelByHandle(handle), name).toDTO()
    }

    fun create(request: AlbumCreationRequest): AlbumDTO {
        val model = Album(
                name = request.name
        )
        model.author = authors.modelByHandle(request.authorHandle)
        model.tracks = request.tracks.map{
            tracks.modelByFilehash(it)
        }
        repository.save(model)
        return model.toDTO()
    }

    fun deleteByAuthorAndName(handle: String, name: String) {
        return repository.deleteByAuthorAndName(authors.modelByHandle(handle), name)
    }
    // TODO:
    // fun update()


    internal fun getById(id: Long) = repository.getReferenceById(id)

    internal fun deleteById(id: Long) = repository.deleteById(id)
}

fun Album.toDTO(): AlbumDTO {
    return AlbumDTO(
            name = this.name,
            tracks = this.tracks.map{
                it.filehash
            }
    )
}