package kt.warmup.musicdb.contollers

import kt.warmup.musicdb.DTO.AuthorDTO
import kt.warmup.musicdb.DTO.AuthorRegistrationRequest
import kt.warmup.musicdb.services.AuthorService
import org.springframework.http.ResponseEntity
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
    fun initRegistration(@RequestBody request: AuthorRegistrationRequest): ResponseEntity<AuthorDTO> {
        return ResponseEntity.ok(service.register(request))
    }

    @DeleteMapping("/{handle}")
    fun deleteById(@PathVariable handle: String): ResponseEntity<Unit> {
        service.deleteByHandle(handle)
        return ResponseEntity.noContent().build()
    }
}