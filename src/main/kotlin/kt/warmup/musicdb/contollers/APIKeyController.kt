package kt.warmup.musicdb.contollers

import kt.warmup.musicdb.DTO.AccountRegistrationRequest
import kt.warmup.musicdb.DTO.AuthorizationRequest
import kt.warmup.musicdb.services.APIKeyService
import kt.warmup.musicdb.services.AccountService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/key")
class APIKeyController(
        val apiKeyService: APIKeyService,
        val accountService: AccountService,
) {
    @PostMapping("/anonymous")
    fun issueAnonymous(): ResponseEntity<String> {
        return ResponseEntity.ok(apiKeyService.issueAnonymous())
    }

    @PostMapping("/account")
    fun issueCreator(@RequestBody body: AuthorizationRequest): ResponseEntity<String> {
        return ResponseEntity.ok(
                apiKeyService.issueAccount(accountService.authorize(body))
        )
    }

    @PostMapping("/register")
    fun registerAccount(@RequestBody body: AccountRegistrationRequest): ResponseEntity<String> {
        val account = accountService.register(body)
        return ResponseEntity.ok(apiKeyService.issueAccount(account))
    }
}