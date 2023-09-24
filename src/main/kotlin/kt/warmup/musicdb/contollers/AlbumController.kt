package kt.warmup.musicdb.contollers

import kt.warmup.musicdb.DTO.AlbumCreationRequest
import kt.warmup.musicdb.DTO.AlbumDTO
import kt.warmup.musicdb.services.AlbumService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/album")
class AlbumController(
        val service: AlbumService,
) {
    @GetMapping
    fun getAll(): ResponseEntity<Collection<AlbumDTO>> {
        return ResponseEntity.ok(
                service.getAll()
        )
    }

    @GetMapping("/{handle}")
    fun getAllByAuthor(@PathVariable handle: String): ResponseEntity<Collection<AlbumDTO>> {
        return ResponseEntity.ok(service.getByAuthorHandle(handle))
    }

    @GetMapping("/{handle}/{name}")
    fun getByAuthorAndName(@PathVariable handle: String, @PathVariable name: String): ResponseEntity<AlbumDTO> {
        return ResponseEntity.ok(service.getByAuthorAndName(handle, name))
    }

    @PostMapping
    fun create(@RequestBody request: AlbumCreationRequest): ResponseEntity<AlbumDTO> {
        return ResponseEntity.ok(
                service.create(request)
        )
    }

    @DeleteMapping("/{handle}/{name}")
    fun deleteByAuthorAndName(@PathVariable handle: String, @PathVariable name: String) {
        service.deleteByAuthorAndName(handle, name)
    }
}