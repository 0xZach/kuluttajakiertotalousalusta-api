package com.turku.payload

data class SkillLevelPayload (
    val skillId: Int,
    val minSkillEN: String? = null,
    val minSkillFI: String? = null,
)