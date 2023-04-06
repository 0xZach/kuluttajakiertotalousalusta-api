package com.turku.payload

data class LogsPayload (
    val logsId: Int,
    val logTimestamp: String? = null,
    val keywordEn: String? = null,
    val keywordFi: String? = null,
    val destinationUrl: String? = null,
    val serviceName: String? = null,
    val serviceTypeName: String? = null,
)