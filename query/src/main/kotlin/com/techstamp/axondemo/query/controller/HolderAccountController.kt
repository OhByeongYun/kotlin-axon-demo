package com.techstamp.axondemo.query.controller

import com.techstamp.axondemo.query.service.QueryService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HolderAccountController(
    private val queryService: QueryService
) {

    @PostMapping("/reset")
    fun reset() {
        queryService.reset()
    }
}
