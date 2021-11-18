package com.techstamp.axondemo.common.events

import org.axonframework.serialization.Revision

@Revision("1.0")
data class HolderCreationEvent(
    val holderID: String,
    val holderName: String,
    val tel: String,
    val address: String,
    val company: String
)
