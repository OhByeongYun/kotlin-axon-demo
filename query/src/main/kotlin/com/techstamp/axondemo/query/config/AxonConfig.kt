package com.techstamp.axondemo.query.config

import org.axonframework.config.EventProcessingConfigurer
import org.axonframework.eventhandling.TrackingEventProcessorConfiguration
import org.axonframework.eventhandling.async.SequentialPerAggregatePolicy
import org.springframework.beans.factory.annotation.Autowired
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
}
