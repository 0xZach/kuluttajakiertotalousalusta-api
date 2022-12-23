package com.turku.models

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object ProblemServiceTable : LongIdTable("problem_services", "id") {
    val appProblemId = long("app_problem_id")
    val appItemId = long("app_item_id")
    val appCategoryId = long("app_category_id")
    val problemId = long("problem_id")
    val itemId = long("item_id")
    val serviceId = long("service_id")
    val createdAt = long("created_at")
    val updatedAt = long("updated_at")
}

class ProblemServiceModel(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<ProblemServiceModel>(ProblemServiceTable)

    var appProblemId by ProblemServiceTable.appProblemId
    var appItemId by ProblemServiceTable.appItemId
    var appCategoryId by ProblemServiceTable.appCategoryId
    var itemId by ProblemServiceTable.itemId
    var problemId by ProblemServiceTable.problemId
    var serviceId by ProblemServiceTable.serviceId
    var updatedAt by ProblemServiceTable.updatedAt
    var createdAt by ProblemServiceTable.createdAt

    fun toProblemService() = ProblemService(
        id = id.value,
        appProblemId = appProblemId,
        appItemId = appItemId,
        appCategoryId = appCategoryId,
        itemId = itemId,
        problemId = problemId,
        serviceId = serviceId,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

data class ProblemService(
    val id: Long? = null,
    val appProblemId: Long? = null,
    val appItemId: Long? = null,
    val appCategoryId: Long? = null,
    val itemId: Long? = null,
    val problemId: Long? = null,
    val serviceId: Long? = null,
    val createdAt: Long? = null,
    val updatedAt: Long? = null,
)
