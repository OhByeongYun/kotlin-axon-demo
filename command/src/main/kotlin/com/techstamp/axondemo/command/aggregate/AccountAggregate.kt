package com.techstamp.axondemo.command.aggregate

import com.fasterxml.jackson.annotation.JsonIgnore
import com.techstamp.axondemo.command.commands.AccountCreationCommand
import com.techstamp.axondemo.command.commands.DepositMoneyCommand
import com.techstamp.axondemo.command.commands.WithdrawMoneyCommand
import com.techstamp.axondemo.common.events.AccountCreationEvent
import com.techstamp.axondemo.common.events.DepositMoneyEvent
import com.techstamp.axondemo.common.events.WithdrawMoneyEvent
import com.techstamp.axondemo.common.logger.LoggerDelegate
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate

// FIXME: Make Aggregate to immutable.
@Aggregate
class AccountAggregate {

    @AggregateIdentifier
    var accountID: String? = null
    var holderID: String? = null
    var balance: Long? = null

    @get:JsonIgnore
    private val logger by LoggerDelegate()

    constructor()

    @CommandHandler
    constructor(command: AccountCreationCommand) : this() {
        logger.info("handling { $command }")

        AggregateLifecycle.apply(
            AccountCreationEvent(
                holderID = command.holderID,
                accountID = command.accountID
            )
        )
    }

    @EventSourcingHandler
    protected fun createAccount(event: AccountCreationEvent) {
        logger.info("applying { $event }")
        this.accountID = event.accountID
        this.holderID = event.holderID
        this.balance = 0
    }

    @CommandHandler
    protected fun depositMoney(command: DepositMoneyCommand) {
        logger.info("handling { $command }")

        if (command.amount <= 0) {
            throw IllegalStateException("금액은 0 이상이어야 합니다.")
        }

        AggregateLifecycle.apply(
            DepositMoneyEvent(
                holderID = command.holderID,
                accountID = command.accountID,
                amount = command.amount
            )
        )
    }

    @EventSourcingHandler
    protected fun depositMoney(event: DepositMoneyEvent) {
        logger.info("applying { $event }")
        this.balance = this.balance?.plus(event.amount)
    }

    @CommandHandler
    protected fun withdrawMoney(command: WithdrawMoneyCommand) {
        logger.info("handling { $command }")

        if (this.balance?.minus(command.amount)!! < 0) {
            throw IllegalStateException("잔고가 부족합니다.")
        } else if (command.amount <= 0) {
            throw IllegalStateException("금액은 0 이상이어야 합니다.")
        }

        AggregateLifecycle.apply(
            WithdrawMoneyEvent(
                holderID = command.holderID,
                accountID = command.accountID,
                amount = command.amount
            )
        )
    }

    @EventSourcingHandler
    protected fun withdrawMoney(event: WithdrawMoneyEvent) {
        logger.info("applying { $event }")
        this.balance = this.balance?.minus(event.amount)
    }
}
