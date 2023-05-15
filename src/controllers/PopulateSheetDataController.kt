package com.turku.controllers

import com.turku.DatabaseFactory
import com.turku.common.*
import com.turku.models.*
import com.turku.payload.PopulateSheetDataPayload
import com.turku.repositories.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import kotlinx.coroutines.delay

suspend fun populateSheetData(call: ApplicationCall) {
    try {
        val adminSecret = call.request.headers["x-admin-secret"]
        if (adminSecret != Utils.getEnv("ktor.admin.adminSecret")) {
            return call.respondUnAuthorized()
        }

        val body = call.receive<PopulateSheetDataPayload>()
        val sheet = SpreadSheetUtil(body.sheetId)
        val skillLevels = sheet.getSkillLevels()
        val contentTypes = sheet.getContentTypes()
        val categories = sheet.getCategories()
        val items = sheet.getItems()
        val problems = sheet.getProblems()
        val results = sheet.getResults()
        val services = sheet.getServices()
        val serviceTypes = sheet.getServiceTypes()
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
                "TRUNCATE categories, items, problem_services, problems, results, services, logs, postal, skill_level, content_type RESTART identity CASCADE;"

            truncateTables.queryDB()
        }

        skillLevels.forEach{ itSkill ->
            SkillLevelRepo.create(
                SkillLevel(
                    appId = itSkill.appId,
                    label = itSkill.minSkillEN,
                    lang = "en-GB",
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis()
                )
            )

            SkillLevelRepo.create(
                SkillLevel(
                    appId = itSkill.appId,
                    label = itSkill.minSkillFI,
                    lang = "fi-FI",
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis()
                )
            )
        }


        contentTypes.forEach{itContent ->
            ContentTypeRepo.create(
                ContentType(
                    appId = itContent.appId,
                    label = itContent.contentTypeEN,
                    lang = "en-GB",
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis()
                )
            )
            ContentTypeRepo.create(
                ContentType(
                    appId = itContent.appId,
                    label = itContent.contentTypeFI,
                    lang = "fi-FI",
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis()
                )
            )
        }


        serviceTypes.forEach{ itServiceType ->
            ServiceTypeRepo.create(
                ServiceType(
                    appId = itServiceType.appId,
                    typeName = itServiceType.typeEN,
                    lang = "en-GB",
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis()
                )
            )
            ServiceTypeRepo.create(
                ServiceType(
                    appId = itServiceType.appId,
                    typeName = itServiceType.typeFI,
                    lang = "fi-FI",
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis()
                )
            )
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
                                id = itResultEn.resultId.toLong(),
                                lang = "en-GB",
                                appContentTypeId = itResultEn.contentTypeId?.toLong(),
                                appSkillLevelId = itResultEn.skillLevelId?.toLong(),
                                minCost = itResultEn.minCostEuro,
                                tutorialIntro = itResultEn.tutorialIntroEN,
                                tutorialUrl = itResultEn.tutorialUrl,
                                tutorialName = itResultEn.tutorialNameEN,
                                minTime = itResultEn.minTime,
                                tutorialImage = itResultEn.tutorialImage,
                                problemId = problemEn.id,
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
                                id = itResultFi.resultId.toLong(),
                                lang = "fi-FI",
                                appContentTypeId = itResultFi.contentTypeId?.toLong(),
                                appSkillLevelId = itResultFi.skillLevelId?.toLong(),
                                minCost = itResultFi.minCostEuro,
                                tutorialIntro = itResultFi.tutorialIntroFI,
                                tutorialUrl = itResultFi.tutorialUrl,
                                tutorialName = itResultFi.tutorialNameFI,
                                minTime = itResultFi.minTime,
                                tutorialImage = itResultFi.tutorialImage,
                                problemId = problemFi.id,
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
                    id = itService.appServiceId,
                    appHriId = itService.appHriId,
                    appBusinessId = itService.appBusinessId,
                    appServiceTypeId = itService.appServiceTypeId,
                    lang = "en-GB",
                    name = itService.nameEN,
                    url = itService.url,
                    latitude = itService.latitude,
                    longitude = itService.longitude,
                    address = itService.address,
                    phone = itService.phone,
                    email = itService.email,
                    details = itService.otherDetailsEN,
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis()
                )
            )

            val serviceFi = ServiceRepo.create(
                Service(
                    id = itService.appServiceId,
                    appHriId = itService.appHriId,
                    appBusinessId = itService.appBusinessId,
                    appServiceTypeId = itService.appServiceTypeId,
                    lang = "fi-FI",
                    name = itService.nameFI,
                    url = itService.url,
                    latitude = itService.latitude,
                    longitude = itService.longitude,
                    address = itService.address,
                    phone = itService.phone,
                    email = itService.email,
                    details = itService.otherDetailsFI,
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis()
                )
            )

            val savedProblems = ProblemRepo.getByAppProblemId(itService.problemIds)

            savedProblems.forEach { itProblem ->

                ProblemServiceRepo.create(
                    ProblemService(
                        problemId = itProblem.id,
                        serviceId = serviceEn.id,
                        createdAt = System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis()
                    )
                )

                ProblemServiceRepo.create(
                    ProblemService(
                        problemId = itProblem.id,
                        serviceId = serviceFi.id,
                        createdAt = System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis()
                    )
                )
            }
        }

        // only need to create the replica of the logs already in the Google sheet
        logs.forEach{ itLogs ->
            LogsRepo.create(
                Logs(
                    id = itLogs.logsId.toLong(),
                    logTimestamp = itLogs.logTimestamp,
                    serviceOrTutorial = itLogs.serviceOrTutorial,
                    resultId = itLogs.resultId!!.toLong(),
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
