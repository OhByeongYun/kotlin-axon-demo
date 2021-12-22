package com.techstamp.axondemo.query.service

import com.techstamp.axondemo.query.entity.HolderAccountSummary
import com.techstamp.axondemo.query.query.AccountQuery
import org.axonframework.config.Configuration
import org.axonframework.eventhandling.TrackingEventProcessor
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.stereotype.Service

@Service
class QueryServiceImpl(
    private val configuration: Configuration,
    private val queryGateway: QueryGateway
) : QueryService {

    override fun reset() {
        configuration.eventProcessingConfiguration()
            .eventProcessorByProcessingGroup(
                "accounts",
                TrackingEventProcessor::class.java
            )
            .ifPresent {
                it.shutDown()
                it.resetTokens()
                it.start()
            }
    }

    override fun getAccountInfo(holderId: String): HolderAccountSummary {
        val accountQuery = AccountQuery(holderId)
        return queryGateway.query(
            accountQuery,
            ResponseTypes.instanceOf(HolderAccountSummary::class.java)
        ).join()
    }
}
