package com.turku.payload

data class ContentTypePayload (
    val appId: Long,
    val contentTypeEN: String? = null,
    val contentTypeFI: String? = null,
)