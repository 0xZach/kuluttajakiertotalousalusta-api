package com.turku.controllers

import com.turku.DatabaseFactory
import com.turku.common.*
import com.turku.models.*
import com.turku.payload.PopulateSheetDataPayload
import com.turku.repositories.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import kotlinx.coroutines.delay
import java.sql.Timestamp
import java.time.LocalDate

suspend fun populateSheetData(call: ApplicationCall) {
    try {
        val adminSecret = call.request.headers["x-admin-secret"]
        if (adminSecret != Utils.getEnv("ktor.admin.adminSecret")) {
            return call.respondUnAuthorized()
        }

        val body = call.receive<PopulateSheetDataPayload>()
        val sheet = SpreadSheetUtil(body.sheetId)
        val categories = sheet.getCategories()
        val items = sheet.getItems()
        val problems = sheet.getProblems()
        val results = sheet.getResults()
        val services = sheet.getServices()
        val logs = sheet.getLogs()
        val postals = sheet.getPostal()

        val dumpProcess = Runtime.getRuntime().exec("sh dump.sh")
        var counter = 0

        while (dumpProcess.isAlive) {
            if (counter >= 60) {
                println("++++++ ********** ---------- DUMP TIMEOUT ---------- ++++++++++ =================")
                return call.respondSomethingWentWrong()
            }
            println("++++++ ********** ---------- DUMP IN PROCESS, $counter SEC ---------- ++++++++++ =================")
            delay(1000)
            counter++
        }

        /*uploadToS3(
            keyName = "${Timestamp(System.currentTimeMillis()).toString().replace(' ', 'T')}.sql",
            filePath = "/opt/api/dumps/${LocalDate.now()}.sql",
            contentType = ContentType.Text.Html.toString()
        )*/

        DatabaseFactory.dbQuery {
            val truncateTables =
                "TRUNCATE categories, items, problem_services, problems, results, services, logs, postal RESTART identity CASCADE;"

            truncateTables.queryDB()
        }

        categories.forEach {
            val categoryEn = CategoryRepo.create(
                Category(
                    lang = "en-GB",
                    appCategoryId = it.categoryId.toLong(),
                    icon = it.icon,
                    name = it.categoryEN,
                    updatedAt = System.currentTimeMillis(),
                    createdAt = System.currentTimeMillis()
                )
            )

            val categoryFi = CategoryRepo.create(
                Category(
                    lang = "fi-FI",
                    appCategoryId = it.categoryId.toLong(),
                    icon = it.icon,
                    name = it.categoryFI,
                    updatedAt = System.currentTimeMillis(),
                    createdAt = System.currentTimeMillis()
                )
            )

            val itemsByCategoryEn = items.filter { itItem ->
                itItem.categoryId == categoryEn.appCategoryId!!.toInt()
            }

            val itemsByCategoryFi = items.filter { itItem ->
                itItem.categoryId == categoryFi.appCategoryId!!.toInt()
            }

            itemsByCategoryEn.forEach { itItemEn ->
                val itemEn = ItemRepo.create(
                    Item(
                        lang = "en-GB",
                        appItemId = itItemEn.itemId.toLong(),
                        appCategoryId = categoryEn.appCategoryId,
                        name = itItemEn.itemEN,
                        icon = itItemEn.icon,
                        categoryId = categoryEn.id,
                        updatedAt = System.currentTimeMillis(),
                        createdAt = System.currentTimeMillis()
                    )
                )

                val problemsByItemEn = problems.filter { itProblem ->
                    itProblem.itemId == itemEn.appItemId!!.toInt()
                }

                problemsByItemEn.forEach { itProblemEn ->
                    val problemEn = ProblemRepo.create(
                        Problem(
                            lang = "en-GB",
                            appProblemId = itProblemEn.problemId.toLong(),
                            appItemId = itemEn.appItemId,
                            appCategoryId = categoryEn.appCategoryId,
                            itemId = itemEn.id,
                            problem = itProblemEn.objectProblemEN,
                            searchTerms = itProblemEn.searchTermsEN,
                            icon = itProblemEn.icon,
                            updatedAt = System.currentTimeMillis(),
                            createdAt = System.currentTimeMillis()
                        )
                    )

                    val resultsEn = results.filter { itResult ->
                        itResult.problemId == problemEn.appProblemId!!.toInt()
                    }

                    resultsEn.forEach { itResultEn ->
                        ResultRepo.create(
                            Result(
                                appResultId = itResultEn.resultId.toLong(),
                                lang = "en-GB",
                                appProblemId = problemEn.appProblemId,
                                appCategoryId = categoryEn.appCategoryId,
                                appItemId = itemEn.appItemId,
                                appContentTypeId = itResultEn.contentTypeId?.toLong(),
                                appSkillLevelId = itResultEn.skillLevelId?.toLong(),
                                minCost = itResultEn.minCostEuro,
                                minSkill = itResultEn.minSkillEN,
                                tutorialIntro = itResultEn.tutorialIntroEN,
                                tutorialUrl = itResultEn.tutorialUrl,
                                tutorialName = itResultEn.tutorialNameEN,
                                minTime = itResultEn.minTime,
                                tutorialImage = itResultEn.tutorialImage,
                                contentType = itResultEn.contentTypeEN,
                                problemId = problemEn.id,
                                itemId = itemEn.id,
                                categoryId = categoryEn.id,
                                updatedAt = System.currentTimeMillis(),
                                createdAt = System.currentTimeMillis()
                            )
                        )
                    }
                }
            }

            itemsByCategoryFi.forEach { itItemFi ->
                val itemFi = ItemRepo.create(
                    Item(
                        lang = "fi-FI",
                        appItemId = itItemFi.itemId.toLong(),
                        appCategoryId = categoryFi.appCategoryId,
                        name = itItemFi.itemFI,
                        icon = itItemFi.icon,
                        categoryId = categoryFi.id,
                        updatedAt = System.currentTimeMillis(),
                        createdAt = System.currentTimeMillis()
                    )
                )

                val problemsByItemFi = problems.filter { itProblem ->
                    itProblem.itemId == itemFi.appItemId!!.toInt()
                }

                problemsByItemFi.forEach { itProblemFi ->
                    val problemFi = ProblemRepo.create(
                        Problem(
                            lang = "fi-FI",
                            appProblemId = itProblemFi.problemId.toLong(),
                            appItemId = itemFi.appItemId,
                            appCategoryId = categoryEn.appCategoryId,
                            itemId = itemFi.id,
                            problem = itProblemFi.objectProblemFI,
                            searchTerms = itProblemFi.searchTermsFI,
                            icon = itProblemFi.icon,
                            updatedAt = System.currentTimeMillis(),
                            createdAt = System.currentTimeMillis()
                        )
                    )

                    val resultsFi = results.filter { itResult ->
                        itResult.problemId == problemFi.appProblemId!!.toInt()
                    }

                    resultsFi.forEach { itResultFi ->
                        ResultRepo.create(
                            Result(
                                appResultId = itResultFi.resultId.toLong(),
                                lang = "fi-FI",
                                appProblemId = problemFi.appProblemId,
                                appCategoryId = categoryFi.appCategoryId,
                                appItemId = itemFi.appItemId,
                                appContentTypeId = itResultFi.contentTypeId?.toLong(),
                                appSkillLevelId = itResultFi.skillLevelId?.toLong(),
                                minCost = itResultFi.minCostEuro,
                                minSkill = itResultFi.minSkillFI,
                                tutorialIntro = itResultFi.tutorialIntroFI,
                                tutorialUrl = itResultFi.tutorialUrl,
                                tutorialName = itResultFi.tutorialNameFI,
                                minTime = itResultFi.minTime,
                                tutorialImage = itResultFi.tutorialImage,
                                contentType = itResultFi.contentTypeFI,
                                problemId = problemFi.id,
                                itemId = itemFi.id,
                                categoryId = categoryFi.id,
                                updatedAt = System.currentTimeMillis(),
                                createdAt = System.currentTimeMillis()
                            )
                        )
                    }
                }
            }
        }

        services.forEach { itService ->
            val serviceEn = ServiceRepo.create(
                Service(
                    appServiceId = itService.appServiceId,
                    appHriId = itService.appHriId,
                    appBusinessId = itService.appBusinessId,
                    appServiceTypeId = itService.appServiceTypeId,
                    serviceTypeName = itService.serviceTypeNameEn,
                    lang = "en-GB",
                    name = itService.nameEN,
                    url = itService.url,
                    latitude = itService.latitude,
                    longitude = itService.longitude,
                    address = itService.address,
                    phone = itService.phone,
                    email = itService.email,
                    details = itService.otherDetailsEN
                )
            )

            val serviceFi = ServiceRepo.create(
                Service(
                    appServiceId = itService.appServiceId,
                    appHriId = itService.appHriId,
                    appBusinessId = itService.appBusinessId,
                    appServiceTypeId = itService.appServiceTypeId,
                    serviceTypeName = itService.serviceTypeNameFi,
                    lang = "fi-FI",
                    name = itService.nameFI,
                    url = itService.url,
                    latitude = itService.latitude,
                    longitude = itService.longitude,
                    address = itService.address,
                    phone = itService.phone,
                    email = itService.email,
                    details = itService.otherDetailsFI
                )
            )

            val savedProblems = ProblemRepo.getByAppProblemId(itService.problemIds)

            savedProblems.forEach { itProblem ->
                if (itProblem.lang == "en-GB") {
                    ProblemServiceRepo.create(
                        ProblemService(
                            itemId = itProblem.itemId,
                            problemId = itProblem.id,
                            serviceId = serviceEn.id,
                            appProblemId = itProblem.appProblemId,
                            appCategoryId = itProblem.appCategoryId,
                            appItemId = itProblem.appItemId,
                        )
                    )
                }

                if (itProblem.lang == "fi-FI") {
                    ProblemServiceRepo.create(
                        ProblemService(
                            itemId = itProblem.itemId,
                            problemId = itProblem.id,
                            serviceId = serviceFi.id,
                            appProblemId = itProblem.appProblemId,
                            appCategoryId = itProblem.appCategoryId,
                            appItemId = itProblem.appItemId,
                        )
                    )
                }
            }
        }

        // only need to create the replica of the logs already in the Google sheet
        logs.forEach{ itLogs ->
            LogsRepo.create(
                Logs(
                    id = itLogs.logsId.toLong(),
                    logsTime = itLogs.logsTime,
                    keywordEn = itLogs.keywordEn,
                    keywordFi = itLogs.keywordFi,
                    destinationUrl = itLogs.destinationUrl,
                    serviceName = itLogs.serviceName,
                    serviceTypeName = itLogs.serviceTypeName,
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis()
                )
            )
        }


        postals.forEach {
            PostalRepo.create(
                Postal(
                    lang = "FI",
                    id = it.postalId.toLong(),
                    postalCode = it.postalCode,
                    streetName = it.streetNameFI,
                    municipality = it.municipalityFI,
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis(),
                )
            )

            PostalRepo.create(
                Postal(
                    lang = "SV",
                    id = it.postalId.toLong(),
                    postalCode = it.postalCode,
                    streetName = it.streetNameSV,
                    municipality = it.municipalitySV,
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis(),
                )
            )
        }

        call.respondSuccess("Migrated successfully", "CATEGORIES")
    } catch (error: Exception) {
        ApplicationLogger.api("Failed to fetch categories", error.printStackTrace())
        call.respondSomethingWentWrong()
    }
}
