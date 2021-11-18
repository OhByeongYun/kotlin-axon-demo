package com.techstamp.axondemo.command.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class DepositDTO(
    @JsonProperty("accountID")
    override val accountID: String,
    @JsonProperty("holderID")
    override val holderID: String,
    override val amount: Long
) : TransactionDTO
