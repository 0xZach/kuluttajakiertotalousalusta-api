package com.turku.models

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object LogsTable : LongIdTable("logs", "id") {
    val logTimestamp = varchar("log_timestamp", 255).nullable()
    val serviceOrTutorial = varchar("service_or_tuto", 255).nullable()
    val resultId = long("result_id").nullable()
    val createdAt = long("created_at")
    val updatedAt = long("updated_at")
}

class LogsModel(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<LogsModel>(LogsTable)

    var logTimestamp by LogsTable.logTimestamp
    var serviceOrTutorial by LogsTable.serviceOrTutorial
    var resultId by LogsTable.resultId
    var createdAt by LogsTable.createdAt
    var updatedAt by LogsTable.updatedAt

    fun toLogs() = Logs(
        id = id.value,
        logTimestamp = logTimestamp,
        serviceOrTutorial = serviceOrTutorial,
        resultId = resultId,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

data class Logs(
    val id: Long? = null,
    val logTimestamp: String? = null,
    val serviceOrTutorial: String? = null,
    val resultId: Long? = null,
    val createdAt: Long,
    val updatedAt: Long,
)
