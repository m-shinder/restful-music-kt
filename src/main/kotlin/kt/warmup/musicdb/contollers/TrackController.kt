package kt.warmup.musicdb.contollers

import kt.warmup.musicdb.DTO.request.create.TrackCreationRequest
import kt.warmup.musicdb.DTO.request.modify.TrackModifyRequest
import kt.warmup.musicdb.DTO.response.TrackDTO
import kt.warmup.musicdb.services.TrackService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
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
    @PreAuthorize("hasAuthority('CREATE_OVER_' + #body.authorHandle)")
    fun createFromBody(@RequestBody body: TrackCreationRequest): ResponseEntity<TrackDTO> {
        return ResponseEntity.ok(service.create(body))
    }

    @PatchMapping("/{hash}")
    @PreAuthorize("hasAuthority('UPDATE_OVER_' + @trackService.getByFilehash(#hash).authorHandle)")
    fun update(@PathVariable hash: String, @RequestBody body: TrackModifyRequest): ResponseEntity<TrackDTO> {
        return ResponseEntity.ok(service.updateByFilehash(hash, body))
    }

    @DeleteMapping("/{hash}")
    @PreAuthorize("hasAuthority('DELETE_OVER_' + @trackService.getByFilehash(#hash).authorHandle)")
    fun deleteById(@PathVariable hash: String): ResponseEntity<Unit> {
        service.deleteByHash(hash)
        return ResponseEntity.noContent().build()
    }



}