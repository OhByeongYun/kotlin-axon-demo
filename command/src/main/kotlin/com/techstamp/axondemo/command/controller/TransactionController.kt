package com.techstamp.axondemo.command.controller

import com.techstamp.axondemo.command.dto.AccountDTO
import com.techstamp.axondemo.command.dto.DepositDTO
import com.techstamp.axondemo.command.dto.HolderDTO
import com.techstamp.axondemo.command.dto.WithdrawalDTO
import com.techstamp.axondemo.command.service.TransactionService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CompletableFuture

@RestController
class TransactionController(
    private val transactionService: TransactionService
) {

    @PostMapping("/holder")
    fun createHolder(@RequestBody holderDTO: HolderDTO): CompletableFuture<String> {
        return transactionService.createHolder(holderDTO)
    }

    @PostMapping("/account")
    fun createAccount(@RequestBody accountDTO: AccountDTO): CompletableFuture<String> {
        return transactionService.createAccount(accountDTO)
    }

    @PostMapping("/deposit")
    fun deposit(@RequestBody depositDTO: DepositDTO): CompletableFuture<String> {
        return transactionService.depositMoney(depositDTO)
    }

    @PostMapping("/withdrawal")
    fun withdraw(@RequestBody withdrawalDto: WithdrawalDTO): CompletableFuture<String> {
        return transactionService.withdrawMoney(withdrawalDto)
    }
}
