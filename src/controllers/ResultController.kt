package com.turku.controllers

import com.turku.common.ApplicationLogger
import com.turku.common.respondSomethingWentWrong
import com.turku.common.respondSuccess
import com.turku.models.Postal
import com.turku.models.Result
import com.turku.payload.LocalizedResultPayload
import com.turku.repositories.*
import io.ktor.server.application.*
import io.ktor.server.request.*

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
                "itemId" to item?.id,
                "itemName" to item?.name,
                "categoryId" to problem.appCategoryId,
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


suspend fun insertResult(call: ApplicationCall){
    try{
        val body = call.receive<LocalizedResultPayload>()
        ResultRepo.create(
            Result(
                id = ResultRepo.getCount() + 1,
                appResultId = ResultRepo.newAppResultId(),
                problemId = body.problemId.toLong(),
                itemId = body.itemId.toLong(),
                categoryId = body.categoryId.toLong(),
                appContentTypeId = body.contentTypeId?.toLong(),
                appSkillLevelId = body.skillLevelId?.toLong(),
                lang = body.lang,
                tutorialName = body.tutorialName,
                tutorialIntro = body.tutorialIntro,
                tutorialUrl = body.tutorialUrl,
                tutorialImage = body.tutorialImage,
                minCost = body.minCostEuro,
                minTime = body.minTime,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )
        )
        call.respondSuccess("INSERT SUCCESSFUL")
    } catch (error: Exception) {
        ApplicationLogger.api("Failed to insert new result", error.printStackTrace())
        call.respondSomethingWentWrong()
    }

}
