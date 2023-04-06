package com.turku.payload

data class PostalPayload(
    val postalId: Int,
    val postalCode: String? = null,
    val streetNameFI: String? = null,
    val streetNameSV: String? = null,
    val municipalityFI: String? = null,
    val municipalitySV: String? = null,
)

data class StreetPayload(
    val postalCode: String? = null,
    val streetNameFI: String? = null,
    val streetNameSV: String? = null,
)

data class MunicipalityPayload(
    val postalCode: String? = null,
    val municipalityFI: String? = null,
    val municipalitySV: String? = null,
)