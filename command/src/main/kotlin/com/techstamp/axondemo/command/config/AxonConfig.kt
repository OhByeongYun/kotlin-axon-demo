/*
 * FIXME:
 * For test in local instance.
 * When we going to production, we must use axon or others.
 */

package com.techstamp.axondemo.command.config

import com.techstamp.axondemo.command.aggregate.AccountAggregate
import org.axonframework.common.caching.Cache
import org.axonframework.common.caching.WeakReferenceCache
import org.axonframework.common.transaction.TransactionManager
import org.axonframework.eventsourcing.*
import org.axonframework.eventsourcing.eventstore.EventStore
import org.axonframework.modelling.command.Repository
import org.axonframework.springboot.autoconfig.AxonAutoConfiguration
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
@AutoConfigureAfter(AxonAutoConfiguration::class)
class AxonConfig {

    @Bean
    fun aggregateFactory(): AggregateFactory<AccountAggregate?>? {
        return GenericAggregateFactory(AccountAggregate::class.java)
    }

    @Bean
    fun snapshotter(eventStore: EventStore?, transactionManager: TransactionManager?): Snapshotter? {
        return AggregateSnapshotter
            .builder()
            .eventStore(eventStore)
            .aggregateFactories(aggregateFactory())
            .transactionManager(transactionManager)
            .build()
    }

    @Bean
    fun snapshotTriggerDefinition(
        eventStore: EventStore,
        transactionManager: TransactionManager
    ): SnapshotTriggerDefinition {
        val SNAPSHOT_THRESHOLD = 5
        return EventCountSnapshotTriggerDefinition(
            snapshotter(eventStore, transactionManager),
            SNAPSHOT_THRESHOLD
        )
    }

    @Bean
    fun cache(): Cache {
        return WeakReferenceCache()
    }

    @Bean
    fun accountAggregateRepository(
        eventStore: EventStore,
        snapshotTriggerDefinition: SnapshotTriggerDefinition,
        cache: Cache
    ): Repository<AccountAggregate> {
        return CachingEventSourcingRepository
            .builder(AccountAggregate::class.java)
            .eventStore(eventStore)
            .snapshotTriggerDefinition(snapshotTriggerDefinition)
            .cache(cache)
            .build()
    }
}
