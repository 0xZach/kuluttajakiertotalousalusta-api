package com.turku.controllers

import com.turku.common.ApplicationLogger
import com.turku.common.respondError
import com.turku.common.respondSomethingWentWrong
import com.turku.common.respondSuccess
import com.turku.repositories.ProblemRepo
import io.ktor.server.application.*

suspend fun getItemProblems(call: ApplicationCall) {
    val appItemId = call.parameters["appItemId"]!!
    try {
        val problems = ProblemRepo.getProblemsByAppItemId(appItemId.toLong())
        if (problems.isEmpty()) return call.respondError(422, "PROBLEMS_NOT_FOUND")
        call.respondSuccess(problems, "PROBLEMS")
    } catch (error: Exception) {
        ApplicationLogger.api("Failed to fetch problems", error.printStackTrace())
        call.respondSomethingWentWrong()
    }
}
