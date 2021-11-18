package com.techstamp.axondemo.query.repository

import com.techstamp.axondemo.query.entity.HolderAccountSummary
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<HolderAccountSummary, String> {
    fun findByHolderId(holderId: String): HolderAccountSummary?
}
