package com.techstamp.axondemo.command.aggregate

import com.fasterxml.jackson.annotation.JsonIgnore
import com.techstamp.axondemo.command.commands.HolderCreationCommand
import com.techstamp.axondemo.common.events.HolderCreationEvent
import com.techstamp.axondemo.common.logger.LoggerDelegate
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate

// FIXME: Make Aggregate to immutable.
@Aggregate
class HolderAggregate {

    @AggregateIdentifier
    var holderID: String? = null
    var holderName: String? = null
    var tel: String? = null
    var address: String? = null

    @get:JsonIgnore
    private val logger by LoggerDelegate()

    constructor()

    @CommandHandler
    constructor(command: HolderCreationCommand) : this() {
        logger.info("handling { $command }")
        AggregateLifecycle.apply(
            HolderCreationEvent(
                holderID = command.holderID,
                holderName = command.holderName,
                tel = command.tel,
                address = command.address
            )
        )
    }

    @EventSourcingHandler
    protected fun createHolder(
        event: HolderCreationEvent
    ) {
        logger.info("applying { $event }")
        this.holderID = event.holderID
        this.holderName = event.holderName
        this.tel = event.tel
        this.address = event.address
    }
}
