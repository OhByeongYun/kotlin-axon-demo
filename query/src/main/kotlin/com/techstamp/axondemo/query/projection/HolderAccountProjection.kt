package com.techstamp.axondemo.query.projection

import com.techstamp.axondemo.common.events.AccountCreationEvent
import com.techstamp.axondemo.common.events.DepositMoneyEvent
import com.techstamp.axondemo.common.events.HolderCreationEvent
import com.techstamp.axondemo.common.events.WithdrawMoneyEvent
import com.techstamp.axondemo.common.logger.LoggerDelegate
import com.techstamp.axondemo.query.entity.HolderAccountSummary
import com.techstamp.axondemo.query.query.AccountQuery
import com.techstamp.axondemo.query.repository.AccountRepository
import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.AllowReplay
import org.axonframework.eventhandling.EventHandler
import org.axonframework.eventhandling.ResetHandler
import org.axonframework.eventhandling.Timestamp
import org.axonframework.queryhandling.QueryHandler
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.EnableRetry
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Component
import java.time.Instant

@Component
@EnableRetry
@ProcessingGroup("accounts")
class HolderAccountProjection(
    private val repository: AccountRepository
) {

    private val logger by LoggerDelegate()

    @ResetHandler
    fun resetHolderAccount() {
        logger.info("reset triggered")
        repository.deleteAll()
    }

    @EventHandler
    @AllowReplay
    fun on(event: HolderCreationEvent, @Timestamp instant: Instant) {
        logger.info("projecting {$event}, timestamp {${instant}}")
        val accountSummary = HolderAccountSummary(
            holderId = event.holderID,
            name = event.holderName,
            tel = event.tel,
            address = event.address,
            company = event.company,
            totalBalance = 0,
            accountCnt = 0
        )
        repository.save(accountSummary)
    }

    @EventHandler
    @Retryable(
        value = [NoSuchElementException::class],
        maxAttempts = 5,
        backoff = Backoff(delay = 1000)
    )
    @AllowReplay
    fun on(event: AccountCreationEvent, @Timestamp instant: Instant) {
        logger.info("projecting {$event}, timestamp {$instant}")
        val holderAccount = getHolderAccountSummary(event.holderID)
        holderAccount.accountCnt += 1
    }

    @EventHandler
    @AllowReplay
    fun on(event: DepositMoneyEvent, @Timestamp instant: Instant) {
        logger.info("projecting {$event}, timestamp {$instant}")
        val holderAccountSummary = getHolderAccountSummary(event.holderID)
        holderAccountSummary.totalBalance += event.amount
        repository.save(holderAccountSummary)
    }

    @EventHandler
    @AllowReplay
    fun on(event: WithdrawMoneyEvent, @Timestamp instant: Instant) {
        logger.info("projecting {$event}, timestamp {$instant}")
        val holderAccountSummary = getHolderAccountSummary(event.holderID)
        holderAccountSummary.totalBalance -= event.amount
        repository.save(holderAccountSummary)
    }

    @QueryHandler
    fun on(query: AccountQuery): HolderAccountSummary? {
        logger.info("handling {$query}")
        return query.holderId?.let { repository.findByHolderId(it) }
    }

    private fun getHolderAccountSummary(holderID: String): HolderAccountSummary {
        return repository.findByHolderId(holderID) ?: throw NoSuchElementException("소유주가 존재하지 않습니다. {$holderID}")
    }
}
