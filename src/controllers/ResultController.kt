package com.turku.controllers

import com.turku.common.ApplicationLogger
import com.turku.common.respondSomethingWentWrong
import com.turku.common.respondSuccess
import com.turku.models.*
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

        var skills = mutableListOf<SkillLevel>()
        results.forEach{
            skills.add(
                SkillLevelRepo.getLocalizedById(
                    it.appSkillLevelId!!,
                    lang
                )!!
            )
        }

        var contentTypes = mutableListOf<ContentType>()
        results.forEach{
            contentTypes.add(
                ContentTypeRepo.getLocalizedById(
                    it.appContentTypeId!!,
                    lang
                )!!
            )
        }

        var serviceTypes = mutableListOf<ServiceType>()
        services.forEach{
            serviceTypes.add(
                ServiceTypeRepo.getLocalizedById(
                    it.appServiceTypeId,
                    lang
                )!!
            )
        }


        call.respondSuccess(
            mapOf(
                "results" to results,
                "skills" to skills,
                "contentTypes" to contentTypes,
                "serviceTypes" to serviceTypes,
                "itemId" to item?.id,
                "itemName" to item?.name,
                "categoryId" to item?.appCategoryId,
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
                //appResultId = ResultRepo.newAppResultId(),
                problemId = body.problemId.toLong(),
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
