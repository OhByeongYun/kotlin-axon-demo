package com.techstamp.axondemo.query.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "MV_ACCOUNT")
class HolderAccountSummary {

    @Id
    @Column(name = "holder_id", nullable = false)
    var holderId: String = ""

    @Column(nullable = false)
    var name: String = ""

    @Column(nullable = false)
    var tel: String = ""

    @Column(nullable = false)
    var address: String = ""

    @Column(name = "total_balance", nullable = false)
    var totalBalance: Long = 0L

    @Column(name = "account_cnt", nullable = false)
    var accountCnt: Long = 0L

    constructor()

    constructor(
        holderId: String,
        name: String,
        tel: String,
        address: String,
        totalBalance: Long,
        accountCnt: Long
    ) {
        this.holderId = holderId
        this.name = name
        this.tel = tel
        this.address = address
        this.totalBalance = totalBalance
        this.accountCnt = accountCnt
    }
}
