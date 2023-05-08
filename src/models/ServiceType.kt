package com.turku.models

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object ServiceTypeTable : LongIdTable("service_types", "id") {
    val typeName = varchar("type_name", 255).nullable()
    val lang = varchar("lang", 255).nullable()
    val createdAt = long("created_at")
    val updatedAt = long("updated_at")
}

class ServiceTypeModel(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<ServiceTypeModel>(ServiceTypeTable)

    var typeName by ServiceTypeTable.typeName
    var lang by ServiceTypeTable.lang
    var updatedAt by ServiceTypeTable.updatedAt
    var createdAt by ServiceTypeTable.createdAt

    fun toServiceType() = ServiceType(
        id = id.value,
        typeName = typeName,
        lang = lang,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

data class ServiceType(
    val id: Long? = null,
    val typeName: String? = null,
    val lang: String? = null,
    val createdAt: Long,
    val updatedAt: Long,
)
