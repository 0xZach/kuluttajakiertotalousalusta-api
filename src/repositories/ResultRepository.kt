package com.turku.repositories

import com.turku.DatabaseFactory.dbQuery
import com.turku.common.queryDB
import com.turku.common.queryDBAndMap
import com.turku.models.*
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.max
import org.jetbrains.exposed.sql.selectAll

class ResultRepository {
    suspend fun getById(id: Long): Result? = dbQuery {
        ResultModel.findById(id)?.toResult()
    }

    suspend fun getProblemResults(problemId: Long): List<Result> = dbQuery {
        ResultModel.find { ResultTable.problemId eq problemId }.map { it.toResult() }
    }

    suspend fun getLocalizedProblemResults(problemId: Long, lang: String): List<Result> = dbQuery {
        ResultModel.find { ResultTable.problemId eq problemId and (ResultTable.lang eq lang) }.map { it.toResult() }
    }

    suspend fun getCount(): Long = dbQuery {
        ResultModel.count()
    }

    suspend fun newAppResultId(): Long = dbQuery {
        ResultTable
            .slice(ResultTable.appResultId.max())
            .selectAll()
            .maxByOrNull { ResultTable.appResultId }
            ?.get(ResultTable.appResultId.max())!!.plus(2)
    }
    suspend fun create(result: Result): Result = dbQuery {
        ResultModel.new {
            appResultId = result.appResultId
            appContentTypeId = result.appContentTypeId
            appSkillLevelId = result.appSkillLevelId
            appProblemId = result.appProblemId
            appItemId = result.appItemId
            appCategoryId = result.appCategoryId
            lang = result.lang
            tutorialName = result.tutorialName
            tutorialIntro = result.tutorialIntro
            tutorialUrl = result.tutorialUrl
            contentType = result.contentType
            minCost = result.minCost
            minSkill = result.minSkill
            minTime = result.minTime
            tutorialImage = result.tutorialImage
            problemId = result.problemId
            itemId = result.itemId
            categoryId = result.categoryId
            createdAt = System.currentTimeMillis()
            updatedAt = System.currentTimeMillis()
        }.toResult()
    }

    suspend fun update(result: Result): Result? = dbQuery {
        if (result.id == null) return@dbQuery null
        val data = ResultModel.findById(result.id)

        requireNotNull(data) { "No data found for id ${result.id}" }

        data.appResultId = result.appResultId
        data.appContentTypeId = result.appContentTypeId
        data.appSkillLevelId = result.appSkillLevelId
        data.appProblemId = result.appProblemId
        data.appItemId = result.appItemId
        data.appCategoryId = result.appCategoryId
        data.lang = result.lang
        data.tutorialName = result.tutorialName
        data.tutorialUrl = result.tutorialUrl
        data.contentType = result.contentType
        data.tutorialIntro = result.tutorialIntro
        data.minCost = result.minCost
        data.minSkill = result.minSkill
        data.minTime = result.minTime
        data.tutorialImage = result.tutorialImage
        data.problemId = result.problemId
        data.itemId = result.itemId
        data.categoryId = result.categoryId
        data.updatedAt = System.currentTimeMillis()

        return@dbQuery data.toResult()
    }

    suspend fun delete(id: Long) = dbQuery {
        ResultModel[id].delete()
    }
}
