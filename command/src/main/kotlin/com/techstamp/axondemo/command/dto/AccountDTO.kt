package com.techstamp.axondemo.command.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class AccountDTO(
    @JsonProperty("holderID")
    val holderID: String
)
