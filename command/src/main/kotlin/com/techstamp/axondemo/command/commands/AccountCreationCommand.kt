package com.techstamp.axondemo.command.commands

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class AccountCreationCommand(
    @TargetAggregateIdentifier
    val holderID: String,
    val accountID: String
)
