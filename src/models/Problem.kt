package com.turku.models

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object ProblemTable : LongIdTable("problems", "id") {
    val appProblemId = long("app_problem_id").nullable()
    val appItemId = long("app_item_id").nullable()
    val appCategoryId = long("app_category_id").nullable()
    val lang = varchar("lang", 255).nullable()
    val problem = varchar("problem", 255).nullable()
    val searchTerms = varchar("search_terms", 255).nullable()
    val icon = varchar("icon", 255).nullable()
    val itemId = long("item_id").nullable()
    val createdAt = long("created_at")
    val updatedAt = long("updated_at")
}

class ProblemModel(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<ProblemModel>(ProblemTable)

    var appProblemId by ProblemTable.appProblemId
    var appItemId by ProblemTable.appItemId
    var appCategoryId by ProblemTable.appCategoryId
    var lang by ProblemTable.lang
    var problem by ProblemTable.problem
    var searchTerms by ProblemTable.searchTerms
    var icon by ProblemTable.icon
    var itemId by ProblemTable.itemId
    var updatedAt by ProblemTable.updatedAt
    var createdAt by ProblemTable.createdAt

    fun toProblem() = Problem(
        id = id.value,
        appProblemId = appProblemId,
        appItemId = appItemId,
        appCategoryId = appCategoryId,
        lang = lang,
        problem = problem,
        searchTerms = searchTerms,
        icon = icon,
        itemId = itemId,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

data class Problem(
    val id: Long? = null,
    val appProblemId: Long? = null,
    val appItemId: Long? = null,
    val appCategoryId: Long? = null,
    val lang: String? = null,
    val problem: String? = null,
    val searchTerms: String? = null,
    val icon: String? = null,
    val itemId: Long? = null,
    val createdAt: Long,
    val updatedAt: Long,
)
