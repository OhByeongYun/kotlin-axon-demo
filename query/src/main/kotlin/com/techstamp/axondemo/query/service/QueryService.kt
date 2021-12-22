package com.techstamp.axondemo.query.service

import com.techstamp.axondemo.query.entity.HolderAccountSummary

interface QueryService {
    fun reset()
    fun getAccountInfo(holderId: String): HolderAccountSummary
}
