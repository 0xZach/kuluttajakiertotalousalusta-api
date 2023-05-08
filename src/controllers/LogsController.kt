package com.turku.controllers

import com.turku.common.ApplicationLogger
import com.turku.common.respondError
import com.turku.common.respondSomethingWentWrong
import com.turku.common.respondSuccess
import com.turku.models.Logs
import com.turku.payload.LogsPayload
import com.turku.repositories.LogsRepo
import io.ktor.server.application.*
import io.ktor.server.request.*

suspend fun getLogs(call: ApplicationCall) {
    try {
        val logs = LogsRepo.getAll()
        if (logs.isEmpty()) return call.respondError(422, "LOGS_NOT_FOUND")
        call.respondSuccess(logs, "LOGS")
    } catch (error: Exception) {
        ApplicationLogger.api("Failed to fetch logs", error.printStackTrace())
        call.respondSomethingWentWrong()
    }
}

suspend fun setLogs(call: ApplicationCall){
    try{
        val body = call.receive<LogsPayload>()
        LogsRepo.create(
            Logs(
                id = LogsRepo.getCount() + 1,
                logTimestamp = body.logTimestamp,
                serviceOrTutorial = body.serviceOrTutorial,
                resultId = body.resultId!!.toLong(),
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )
        )
        call.respondSuccess("INSERT SUCCESSFUL")
    } catch (error: Exception) {
        ApplicationLogger.api("Failed to insert new logs", error.printStackTrace())
        call.respondSomethingWentWrong()
    }

}