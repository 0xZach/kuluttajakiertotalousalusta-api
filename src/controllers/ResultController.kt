package com.turku.controllers

import com.turku.common.ApplicationLogger
import com.turku.common.respondSomethingWentWrong
import com.turku.common.respondSuccess
import com.turku.repositories.*
import io.ktor.server.application.*

suspend fun getProblemResults(call: ApplicationCall) {
    val problemId = call.parameters["problemId"]!!
    val latitude = call.parameters["latitude"]!!
    val longitude = call.parameters["longitude"]!!
    val lang = call.request.headers["Accept-Language"]
    try {
        val results = ResultRepo.getLocalizedProblemResults(problemId.toLong(), lang!!)
        val problem = ProblemRepo.getById(problemId.toLong())
        val item = ItemRepo.getById(problem?.itemId!!)
        val services = ServiceRepo.getProblemServicesByRadius(
            problemId = problemId.toLong(), lang = lang, latitude = latitude, longitude = longitude
        )

        call.respondSuccess(
            mapOf(
                "results" to results,
                "itemName" to item?.name,
                "problemName" to problem.problem,
                "services" to services
            ), "RESULTS"
        )
    } catch (error: Exception) {
        ApplicationLogger.api("Failed to fetch results", error.printStackTrace())
        call.respondSomethingWentWrong()
    }
}
