package kt.warmup.musicdb.contollers

import kt.warmup.musicdb.DTO.response.AuthorDTO
import kt.warmup.musicdb.DTO.request.create.AuthorRegistrationRequest
import kt.warmup.musicdb.models.Account
import kt.warmup.musicdb.services.AuthorService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/author")
class AuthorController(
        private val service: AuthorService
) {
    @GetMapping
    fun getAll(): ResponseEntity<Collection<AuthorDTO>> {
        return ResponseEntity.ok(service.getAll())
    }

    @GetMapping("/{handle}")
    fun getById(@PathVariable handle: String): ResponseEntity<AuthorDTO> {
        return ResponseEntity.ok(service.getByHandle(handle))
    }

    @PostMapping
    fun register(
            @RequestBody request: AuthorRegistrationRequest,
            auth: Authentication): ResponseEntity<AuthorDTO> {
        return ResponseEntity.ok(service.register(request, auth.principal as Account))
    }

    @DeleteMapping("/{handle}")
    @PreAuthorize("hasAuthority('DELETE_OVER_' + #handle)")
    fun deleteById(@PathVariable handle: String, auth: Authentication): ResponseEntity<Unit> {
        service.deleteByHandle(handle)
        return ResponseEntity.noContent().build()
    }
}