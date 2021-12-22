package com.techstamp.axondemo.query.controller

import com.techstamp.axondemo.query.entity.HolderAccountSummary
import com.techstamp.axondemo.query.service.QueryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@RestController
class HolderAccountController(
    private val queryService: QueryService
) {

    @PostMapping("/reset")
    fun reset() {
        queryService.reset()
    }

    @GetMapping("/account/info/{id}")
    fun getAccountInfo(
        @PathVariable("id") @NotNull @NotBlank holderId: String
    ): ResponseEntity<HolderAccountSummary> {
        return ResponseEntity.ok()
            .body(queryService.getAccountInfo(holderId))
    }
}
