package com.techstamp.axondemo.command.service

import com.techstamp.axondemo.command.commands.AccountCreationCommand
import com.techstamp.axondemo.command.commands.DepositMoneyCommand
import com.techstamp.axondemo.command.commands.HolderCreationCommand
import com.techstamp.axondemo.command.commands.WithdrawMoneyCommand
import com.techstamp.axondemo.command.dto.AccountDTO
import com.techstamp.axondemo.command.dto.HolderDTO
import com.techstamp.axondemo.command.dto.TransactionDTO
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.CompletableFuture

@Service
class TransactionServiceImpl(
    private val commandGateway: CommandGateway
) : TransactionService {

    override fun createHolder(holderDTO: HolderDTO): CompletableFuture<String> {
        return commandGateway.send(
            HolderCreationCommand(
                holderID = UUID.randomUUID().toString(),
                holderName = holderDTO.holderName,
                tel = holderDTO.tel,
                address = holderDTO.address
            )
        )
    }

    override fun createAccount(accountDTO: AccountDTO): CompletableFuture<String> {
        return commandGateway.send(
            AccountCreationCommand(
                holderID = accountDTO.holderID,
                accountID = UUID.randomUUID().toString()
            )
        )
    }

    override fun depositMoney(transactionDTO: TransactionDTO): CompletableFuture<String> {
        return commandGateway.send(
            DepositMoneyCommand(
                accountID = transactionDTO.accountID,
                holderID = transactionDTO.holderID,
                amount = transactionDTO.amount
            )
        )
    }

    override fun withdrawMoney(transactionDTO: TransactionDTO): CompletableFuture<String> {
        return commandGateway.send(
            WithdrawMoneyCommand(
                accountID = transactionDTO.accountID,
                holderID = transactionDTO.holderID,
                amount = transactionDTO.amount
            )
        )
    }
}
