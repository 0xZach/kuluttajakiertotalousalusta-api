package com.turku.payload

data class LogsPayload (
    val logsId: Int,
    val logTimestamp: String? = null,
    val serviceOrTutorial: String? = null,
    val resultId: Int? = null,
)