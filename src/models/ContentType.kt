package com.turku.models

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object ContentTypeTable : LongIdTable("content_type", "id") {
    val contentTypeEN = varchar("content_type_en", 255).nullable()
    val contentTypeFI = varchar("content_type_fi", 255).nullable()
    val createdAt = long("created_at")
    val updatedAt = long("updated_at")
}

class ContentTypeModel(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<ContentTypeModel>(ContentTypeTable)

    var contentTypeEN by ContentTypeTable.contentTypeEN
    var contentTypeFI by ContentTypeTable.contentTypeFI
    var updatedAt by ContentTypeTable.updatedAt
    var createdAt by ContentTypeTable.createdAt

    fun toContentType() = ContentType(
        id = id.value,
        contentTypeEN = contentTypeEN,
        contentTypeFI = contentTypeFI,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

data class ContentType(
    val id: Long? = null,
    val contentTypeEN: String? = null,
    val contentTypeFI: String? = null,
    val createdAt: Long,
    val updatedAt: Long,
)
