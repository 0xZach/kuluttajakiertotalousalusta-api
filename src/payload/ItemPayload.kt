package com.turku.payload

data class ItemPayload(
    val categoryId: Int,
    val itemId: Int,
    val itemEN: String,
    val itemFI: String,
    val icon: String? = null,
)
