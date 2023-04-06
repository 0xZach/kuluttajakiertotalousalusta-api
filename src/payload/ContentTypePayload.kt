package com.turku.payload

data class ContentTypePayload (
    val typeId: Int,
    val contentTypeEN: String? = null,
    val contentTypeFI: String? = null,
)