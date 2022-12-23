package com.turku.controllers

import com.turku.common.ApplicationLogger
import com.turku.common.respondError
import com.turku.common.respondSomethingWentWrong
import com.turku.common.respondSuccess
import com.turku.payload.MotiveHitsPayload
import com.turku.repositories.MotiveRepo
import io.ktor.server.application.*
import io.ktor.server.request.*


suspend fun getMotivesByCategory(call: ApplicationCall) {
    val category = call.parameters["category"]!!
    try {
        val motives = MotiveRepo.getMotivesByCategory(category)
        if (motives.isEmpty()) return call.respondError(422, "MOTIVES_NOT_FOUND")
        call.respondSuccess(motives, "MOTIVES")
    } catch (error: Exception) {
        ApplicationLogger.api("Failed to fetch motives", error.printStackTrace())
        call.respondSomethingWentWrong()
    }
}

suspend fun updateHits(call: ApplicationCall) {
    try {
        val payload = call.receive<MotiveHitsPayload>()
        MotiveRepo.updateHits(payload.motiveIds)
        call.respondSuccess("HITS_UPDATED")
    } catch (error: Exception) {
        ApplicationLogger.api("Failed to update hits", error.printStackTrace())
        call.respondSomethingWentWrong()
    }
}
