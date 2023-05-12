package com.turku.payload

data class SkillLevelPayload (
    val appId: Long,
    val minSkillEN: String? = null,
    val minSkillFI: String? = null,
)