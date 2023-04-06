package com.turku.controllers

import com.turku.common.ApplicationLogger
import com.turku.common.respondError
import com.turku.common.respondSomethingWentWrong
import com.turku.common.respondSuccess
import com.turku.repositories.SkillLevelRepo
import io.ktor.server.application.*

suspend fun getSkillLevels(call: ApplicationCall) {
    try {
        val skillLevels = SkillLevelRepo.getAll()
        if (skillLevels.isEmpty()) return call.respondError(422, "SKILL_LEVELS_NOT_FOUND")
        call.respondSuccess(mapOf("skillLevels" to skillLevels), "SKILLS")
    } catch (error: Exception) {
        ApplicationLogger.api("Failed to fetch skill levels", error.printStackTrace())
        call.respondSomethingWentWrong()
    }
}