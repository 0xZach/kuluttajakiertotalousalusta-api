package com.turku.controllers

import com.turku.common.ApplicationLogger
import com.turku.common.respondError
import com.turku.common.respondSomethingWentWrong
import com.turku.common.respondSuccess
import com.turku.repositories.ItemRepo
import io.ktor.server.application.*

suspend fun getItemsByCategory(call: ApplicationCall) {
    val appCategoryId = call.parameters["appCategoryId"]!!
    try {
        val items = ItemRepo.getItemsByAppCategory(appCategoryId.toLong())
        if (items.isEmpty()) return call.respondError(422, "ITEMS_NOT_FOUND")
        call.respondSuccess(items, "ITEMS")
    } catch (error: Exception) {
        ApplicationLogger.api("Failed to fetch items", error.printStackTrace())
        call.respondSomethingWentWrong()
    }
}
