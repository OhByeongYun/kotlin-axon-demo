package com.techstamp.axondemo.common.events

data class DepositMoneyEvent(
    val holderID: String,
    val accountID: String,
    val amount: Long
)
