package com.turku.payload

data class ProblemPayload(
    val categoryId: Int,
    val itemId: Int,
    val problemId: Int,
    val objectProblemEN: String? = null,
    val objectProblemFI: String? = null,
    val searchTermsEN: String? = null,
    val searchTermsFI: String? = null,
    val icon: String,
)
