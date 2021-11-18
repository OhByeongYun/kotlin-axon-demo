package com.techstamp.axondemo.command.commands

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class DepositMoneyCommand(
    @TargetAggregateIdentifier
    val accountID: String,
    val holderID: String,
    val amount: Long
)
