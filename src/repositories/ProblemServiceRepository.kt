package com.turku.repositories

import com.turku.DatabaseFactory.dbQuery
import com.turku.models.*

class ProblemServiceRepository {
    suspend fun getById(id: Long): ProblemService? = dbQuery {
        ProblemServiceModel.findById(id)?.toProblemService()
    }

    suspend fun getByProblemId(problemId: Long): List<ProblemService> = dbQuery {
        ProblemServiceModel.find {
            ProblemServiceTable.problemId eq problemId
        }.map { it.toProblemService() }
    }

    suspend fun create(result: ProblemService): ProblemService = dbQuery {
        ProblemServiceModel.new {
            appProblemId = result.appProblemId!!
            appItemId = result.appItemId!!
            appCategoryId = result.appCategoryId!!
            problemId = result.problemId!!
            serviceId = result.serviceId!!
            itemId = result.itemId!!
            createdAt = System.currentTimeMillis()
            updatedAt = System.currentTimeMillis()
        }.toProblemService()
    }

    suspend fun update(result: ProblemService): ProblemService? = dbQuery {
        if (result.id == null) return@dbQuery null
        val data = ProblemServiceModel.findById(result.id)
        requireNotNull(data) { "No data found for id ${result.id}" }
        data.appProblemId = result.appProblemId!!
        data.appItemId = result.appItemId!!
        data.problemId = result.problemId!!
        data.appCategoryId = result.appCategoryId!!
        data.serviceId = result.serviceId!!
        data.itemId = result.itemId!!
        data.updatedAt = System.currentTimeMillis()

        return@dbQuery data.toProblemService()
    }

    suspend fun delete(id: Long) = dbQuery {
        ProblemServiceModel[id].delete()
    }
}
