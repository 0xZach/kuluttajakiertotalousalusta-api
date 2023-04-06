package com.turku.payload

data class ResultPayload(
    val resultId: Int,
    val problemId: Int,
    val categoryId: Int,
    val itemId: Int,
    val contentTypeId: Int? = null,
    val skillLevelId: Int? = null,

    val tutorialUrl: String? = null,
    val minCostEuro: String? = null,

    val minSkillEN: String? = null,
    val minSkillFI: String? = null,

    val minTime: String? = null,

    val tutorialNameEN: String? = null,
    val tutorialNameFI: String? = null,

    val tutorialIntroEN: String? = null,
    val tutorialIntroFI: String? = null,

    val contentTypeEN: String? = null,
    val contentTypeFI: String? = null,

    val tutorialImage: String? = null
)

data class LocalizedResultPayload(
    val resultId: Int,
    val problemId: Int,
    val categoryId: Int,
    val itemId: Int,
    val contentTypeId: Int? = null,
    val skillLevelId: Int? = null,

    val tutorialUrl: String? = null,
    val minCostEuro: String? = null,

    val minSkill: String? = null,

    val minTime: String? = null,

    val tutorialName: String? = null,

    val tutorialIntro: String? = null,

    val contentType: String? = null,

    val tutorialImage: String? = null,

    val lang: String? = null
)
