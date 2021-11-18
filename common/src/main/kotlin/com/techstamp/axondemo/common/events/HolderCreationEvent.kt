package com.techstamp.axondemo.common.events

data class HolderCreationEvent(
    val holderID: String,
    val holderName: String,
    val tel: String,
    val address: String
)
