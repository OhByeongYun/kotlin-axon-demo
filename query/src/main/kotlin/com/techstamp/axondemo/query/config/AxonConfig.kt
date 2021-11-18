package com.techstamp.axondemo.query.config

import com.techstamp.axondemo.query.version.HolderCreationEventV1
import org.axonframework.config.EventProcessingConfigurer
import org.axonframework.eventhandling.TrackingEventProcessorConfiguration
import org.axonframework.eventhandling.async.SequentialPerAggregatePolicy
import org.axonframework.serialization.upcasting.event.EventUpcasterChain
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AxonConfig {

    @Autowired
    fun configure(configurer: EventProcessingConfigurer) {
        configurer.registerTrackingEventProcessor(
            "accounts",
            org.axonframework.config.Configuration::eventStore
        ) {
            TrackingEventProcessorConfiguration
                .forSingleThreadedProcessing()
                .andBatchSize(100)
        }

        configurer.registerSequencingPolicy(
            "accounts"
        ) {
            SequentialPerAggregatePolicy.instance()
        }
    }

    @Bean
    fun eventUpcasterChain(): EventUpcasterChain {
        return EventUpcasterChain(
            HolderCreationEventV1()
        )
    }
}
