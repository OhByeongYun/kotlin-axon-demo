package com.techstamp.axondemo.command.dto

interface TransactionDTO {
    val holderID: String
    val accountID: String
    val amount: Long
}
