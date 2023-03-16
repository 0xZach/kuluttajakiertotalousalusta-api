package com.turku.payload

data class PostalPayload(
    val postalId: Int,
    val postalCode: String,
    val streetNameFI: String,
    val streetNameSV: String,
    val municipalityFI: String,
    val municipalitySV: String,
)

data class StreetPayload(
    val postalCode: String,
    val streetNameFI: String,
    val streetNameSV: String,
)

data class MunicipalityPayload(
    val postalCode: String,
    val municipalityFI: String,
    val municipalitySV: String,
)