package com.techstamp.axondemo.command.commands

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class HolderCreationCommand(
    @TargetAggregateIdentifier
    val holderID: String,
    val holderName: String,
    val tel: String,
    val address: String,
    val company: String
)
