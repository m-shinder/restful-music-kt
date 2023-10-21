package kt.warmup.musicdb.services

import kt.warmup.musicdb.DTO.response.AuthorDTO
import kt.warmup.musicdb.DTO.request.create.AuthorRegistrationRequest
import kt.warmup.musicdb.DTO.request.modify.AuthorModifyRequest
import kt.warmup.musicdb.models.Account
import kt.warmup.musicdb.models.Author
import kt.warmup.musicdb.repos.IAuthorRepo
import org.springframework.stereotype.Service
import kotlin.reflect.full.memberProperties

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

    fun register(author: AuthorRegistrationRequest, account: Account): AuthorDTO {
        val model = Author(name = author.name, handle = author.handle, account = account)
        repository.save(model)
        return model.toDTO()
    }

    fun updateByHandle(handle: String, author: AuthorModifyRequest): AuthorDTO {
        return repository.save(update(
                modelByHandle(handle),
                author
        )).toDTO()
    }

    fun deleteByHandle(handle: String) = repository.deleteById(modelByHandle(handle).id)

    internal fun modelByHandle(handle: String): Author {
        val model = repository.findByHandle(handle)
        return  model
    }

    internal fun update(model: Author, author: AuthorModifyRequest): Author {
        if (author.name != null) model.name = author.name
        if (author.handle != null) model.handle = author.handle

        return model
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