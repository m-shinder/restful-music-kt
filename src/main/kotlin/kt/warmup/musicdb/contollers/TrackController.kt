package kt.warmup.musicdb.contollers

import kt.warmup.musicdb.DTO.TrackCreationRequest
import kt.warmup.musicdb.DTO.TrackDTO
import kt.warmup.musicdb.services.TrackService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/track")
class TrackController(
        private val service: TrackService
) {


    @GetMapping
    fun getAllTracks(): ResponseEntity<Collection<TrackDTO>> {
        return ResponseEntity.ok(service.getAll())
    }

    @GetMapping("/{hash}")
    fun getById(@PathVariable hash: String): ResponseEntity<TrackDTO> {
        return ResponseEntity.ok(service.getByFilehash(hash))
    }

    @PostMapping
    fun createFromBody(@RequestBody body: TrackCreationRequest): ResponseEntity<TrackDTO> {
        return ResponseEntity.ok(service.create(body))
    }

    // XXX: Put does not see req.body in any chance
    @PostMapping("/{hash}")
    fun updateOrCreate(@PathVariable hash: String, @RequestBody body: TrackDTO): ResponseEntity<TrackDTO> {
        return ResponseEntity.ok(service.updateByFilehash(hash, body))
    }

    @DeleteMapping("/{hash}")
    fun deleteById(@PathVariable hash: String): ResponseEntity<Unit> {
        service.deleteByHash(hash)
        return ResponseEntity.noContent().build()
    }



}