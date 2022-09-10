package com.narbase.kabbura.kaian.main.sms

@Suppress("ArrayInDataClass")
data class SmsMessageData(
    val message: String,
    val phones: Array<Long>
)
