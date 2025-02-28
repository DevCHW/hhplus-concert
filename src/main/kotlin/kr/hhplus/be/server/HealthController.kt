package kr.hhplus.be.server

import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthController(
    private val env: Environment
) {

    @GetMapping("/health")
    fun healthCheck(): ResponseEntity<String> {
        val activeProfiles = env.activeProfiles
        val activeProfile = if (activeProfiles.isNotEmpty()) activeProfiles[0] else "Unknown"
        val message = "Hello World! Runtime Profiles = $activeProfile"
        return ResponseEntity.status(HttpStatus.OK).body(message)
    }
}