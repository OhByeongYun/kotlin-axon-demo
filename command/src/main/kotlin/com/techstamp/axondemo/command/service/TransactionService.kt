package com.techstamp.axondemo.command.service

import com.techstamp.axondemo.command.dto.AccountDTO
import com.techstamp.axondemo.command.dto.HolderDTO
import com.techstamp.axondemo.command.dto.TransactionDTO
import java.util.concurrent.CompletableFuture

interface TransactionService {
    fun createHolder(holderDTO: HolderDTO): CompletableFuture<String>
    fun createAccount(accountDTO: AccountDTO): CompletableFuture<String>
    fun depositMoney(transactionDTO: TransactionDTO): CompletableFuture<String>
    fun withdrawMoney(transactionDTO: TransactionDTO): CompletableFuture<String>
}
