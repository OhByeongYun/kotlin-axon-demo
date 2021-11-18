package com.techstamp.axondemo.common.events

data class WithdrawMoneyEvent(
    val holderID: String,
    val accountID: String,
    val amount: Long
)
