package com.example.newkbslserver.test.web

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class TestController {

    @GetMapping("/")
    fun hello(): ResponseEntity<String> {
        return ResponseEntity.ok("Hello World!")
    }

}