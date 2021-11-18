package com.techstamp.axondemo.common.events

data class AccountCreationEvent(
    val holderID: String,
    val accountID: String
)
