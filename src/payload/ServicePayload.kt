package com.turku.payload

data class ServicePayload(
    val appServiceId: Long,
    val appHriId: String,
    val appBusinessId: String,
    val appServiceTypeId: String,
    val serviceTypeNameEn: String,
    val serviceTypeNameFi: String,
    val problemIds: List<Long>,
    val nameEN: String,
    val nameFI: String,
    val url: String? = null,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val phone: String? = null,
    val email: String? = null,
    val otherDetailsEN: String? = null,
    val otherDetailsFI: String? = null,
)
