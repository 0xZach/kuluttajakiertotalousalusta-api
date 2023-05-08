package com.turku.models

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object ProblemServiceTable : LongIdTable("problem_services", "id") {
    val problemId = long("problem_id")
    val serviceId = long("service_id")
    val createdAt = long("created_at")
    val updatedAt = long("updated_at")
}

class ProblemServiceModel(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<ProblemServiceModel>(ProblemServiceTable)

    var problemId by ProblemServiceTable.problemId
    var serviceId by ProblemServiceTable.serviceId
    var updatedAt by ProblemServiceTable.updatedAt
    var createdAt by ProblemServiceTable.createdAt

    fun toProblemService() = ProblemService(
        id = id.value,
        problemId = problemId,
        serviceId = serviceId,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

data class ProblemService(
    val id: Long? = null,
    val problemId: Long? = null,
    val serviceId: Long? = null,
    val createdAt: Long? = null,
    val updatedAt: Long? = null,
)
