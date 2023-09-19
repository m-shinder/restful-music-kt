package kt.warmup.musicdb.services

import kt.warmup.musicdb.DTO.AuthorDTO
import kt.warmup.musicdb.DTO.AuthorRegistrationRequest
import kt.warmup.musicdb.models.Author
import kt.warmup.musicdb.repos.IAuthorRepo
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.stereotype.Service

@Service
class AuthorService(
        private val repository: IAuthorRepo
) {
    fun getAll(): Collection<AuthorDTO> = repository.findAll().map {
        it.toDTO()
    }

    fun getByHandle(handle: String): AuthorDTO = modelByHandle(handle).toDTO()

    fun searchByName(name: String): AuthorDTO? {
        return null;
    }

    fun register(author: AuthorRegistrationRequest): AuthorDTO {
        val model = Author(name = author.name, handle = author.handle)
        repository.save(model)
        return model.toDTO()
    }

    fun updateById(id: Long, author: AuthorDTO): AuthorDTO {
        val model = repository.getReferenceById(id)
        return model.toDTO()
    }

    fun deleteByHandle(handle: String) = repository.deleteById(modelByHandle(handle).id)

    internal fun modelByHandle(handle: String): Author {
        val model = repository.findOne(Example.of(Author(
                name = "",
                handle = handle
        ), ExampleMatcher.matchingAny() )).get()
        return  model
    }

    internal fun getById(id: Long): AuthorDTO = repository.getReferenceById(id).toDTO()

    internal fun deleteById(id: Long) = repository.deleteById(id)
}

fun Author.toDTO(): AuthorDTO = AuthorDTO(
        name = this.name,
        handle = this.handle,
        albums = this.albums.map { it.name },
        tracks = this.tracks.map { it.filehash },
)