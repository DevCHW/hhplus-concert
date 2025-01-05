package kr.hhplus.be.server.support.restdocs

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {

    @GetMapping("/hello")
    fun hello(): Map<String, String> {
        val map = HashMap<String, String>()
        map.put("message", "Hello")
        return map
    }
}