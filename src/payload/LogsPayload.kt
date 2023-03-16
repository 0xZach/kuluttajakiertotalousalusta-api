package com.turku.payload

data class LogsPayload (
    val logsId: Int,
    val logsTime: String,
    val keywordEn: String,
    val keywordFi: String,
    val destinationUrl: String,
    val serviceName: String,
    val serviceTypeName: String,
)