package com.turku.models

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object LogsTable : LongIdTable("logs", "id") {
    val logsTime = varchar("logstime", 255)
    val keywordEn = varchar("keyworden", 255)
    val keywordFi = varchar("keywordfi", 255)
    val destinationUrl = varchar("destinationurl", 255)
    val serviceName = varchar("servicename", 255)
    val serviceTypeName = varchar("servicetypename", 255)
    val createdAt = long("created_at")
    val updatedAt = long("updated_at")
}

class LogsModel(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<LogsModel>(LogsTable)

    var logsTime by LogsTable.logsTime
    var keywordEn by LogsTable.keywordEn
    var keywordFi by LogsTable.keywordFi
    var destinationUrl by LogsTable.destinationUrl
    var serviceName by LogsTable.serviceName
    var serviceTypeName by LogsTable.serviceTypeName
    var createdAt by LogsTable.createdAt
    var updatedAt by LogsTable.updatedAt

    fun toLogs() = Logs(
        id = id.value,
        logsTime = logsTime,
        keywordEn = keywordEn,
        keywordFi = keywordFi,
        destinationUrl = destinationUrl,
        serviceName = serviceName,
        serviceTypeName = serviceTypeName,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

data class Logs(
    val id: Long? = null,
    val logsTime: String,
    val keywordEn: String,
    val keywordFi: String,
    val destinationUrl: String,
    val serviceName: String,
    val serviceTypeName: String,
    val createdAt: Long,
    val updatedAt: Long,
)
