package com.turku.controllers

import com.turku.common.ApplicationLogger
import com.turku.common.respondSomethingWentWrong
import com.turku.common.respondSuccess
import com.turku.models.Postal
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

        // we fetch each municipality data based on a service, so we end up with an array of the same size
        var municipalities = mutableListOf<Postal>()
        services.forEach{
            municipalities.add(
                PostalRepo.getByAddress(
                    if ((it.address.split(",")[1]).toIntOrNull() != null) it.address.split(",")[1] else "00000",
                    it.address.split(" ")[0]
                )[0]!!
            )
        }

        call.respondSuccess(
            mapOf(
                "results" to results,
                "itemName" to item?.name,
                "problemName" to problem.problem,
                "services" to services,
                "municipalities" to municipalities
            ), "RESULTS"
        )
    } catch (error: Exception) {
        ApplicationLogger.api("Failed to fetch results", error.printStackTrace())
        call.respondSomethingWentWrong()
    }
}
