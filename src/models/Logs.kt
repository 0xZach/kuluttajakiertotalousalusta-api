package com.turku.models

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object LogsTable : LongIdTable("logs", "id") {
    val logTimestamp = varchar("log_timestamp", 255).nullable()
    val keywordEn = varchar("keyword_en", 255).nullable()
    val keywordFi = varchar("keyword_fi", 255).nullable()
    val destinationUrl = varchar("destination_url", 255).nullable()
    val serviceName = varchar("service_name", 255).nullable()
    val serviceTypeName = varchar("service_type_name", 255).nullable()
    val createdAt = long("created_at")
    val updatedAt = long("updated_at")
}

class LogsModel(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<LogsModel>(LogsTable)

    var logTimestamp by LogsTable.logTimestamp
    var keywordEn by LogsTable.keywordEn
    var keywordFi by LogsTable.keywordFi
    var destinationUrl by LogsTable.destinationUrl
    var serviceName by LogsTable.serviceName
    var serviceTypeName by LogsTable.serviceTypeName
    var createdAt by LogsTable.createdAt
    var updatedAt by LogsTable.updatedAt

    fun toLogs() = Logs(
        id = id.value,
        logTimestamp = logTimestamp,
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
    val logTimestamp: String? = null,
    val keywordEn: String? = null,
    val keywordFi: String? = null,
    val destinationUrl: String? = null,
    val serviceName: String? = null,
    val serviceTypeName: String? = null,
    val createdAt: Long,
    val updatedAt: Long,
)
