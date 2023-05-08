package com.turku.repositories

import com.turku.DatabaseFactory.dbQuery
import com.turku.models.*
import org.jetbrains.exposed.sql.and

class ProblemRepository {
    suspend fun getById(id: Long): Problem? = dbQuery {
        ProblemModel.findById(id)?.toProblem()
    }

    suspend fun getByAppProblemId(appProblemIds: List<Long>): List<Problem> = dbQuery {
        ProblemModel.find { ProblemTable.appProblemId inList appProblemIds }.map { it.toProblem() }
    }

    suspend fun getItemProblems(itemId: Long): List<Problem> = dbQuery {
        ProblemModel.find { ProblemTable.itemId eq itemId }.map { it.toProblem() }
    }

    suspend fun getProblemsByAppItemId(appItemId: Long): List<Problem> = dbQuery {
        ProblemModel.find { ProblemTable.appItemId eq appItemId }.map { it.toProblem() }
    }

    suspend fun getLocalizedItemProblems(itemId: Long, lang: String): List<Problem> = dbQuery {
        ProblemModel.find { ProblemTable.itemId eq itemId and (ProblemTable.lang eq lang) }.map { it.toProblem() }
    }

    suspend fun create(newProblem: Problem): Problem = dbQuery {
        ProblemModel.new {
            appItemId = newProblem.appItemId
            appProblemId = newProblem.appProblemId
            problem = newProblem.problem
            searchTerms = newProblem.searchTerms
            lang = newProblem.lang
            icon = newProblem.icon
            itemId = newProblem.itemId
            createdAt = System.currentTimeMillis()
            updatedAt = System.currentTimeMillis()
        }.toProblem()
    }

    suspend fun update(problem: Problem): Problem? = dbQuery {
        if (problem.id == null) return@dbQuery null
        val data = ProblemModel.findById(problem.id)
        requireNotNull(data) { "No data found for id ${problem.id}" }

        data.appProblemId = problem.appProblemId
        data.appItemId = problem.appItemId
        data.problem = problem.problem
        data.searchTerms = problem.searchTerms
        data.lang = problem.lang
        data.icon = problem.icon
        data.itemId = problem.itemId
        data.updatedAt = System.currentTimeMillis()

        return@dbQuery data.toProblem()
    }

    suspend fun delete(id: Long) = dbQuery {
        ProblemModel[id].delete()
    }
}
