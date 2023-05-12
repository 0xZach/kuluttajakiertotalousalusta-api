package com.turku.models

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object ContentTypeTable : LongIdTable("content_type", "id") {
    val appId = long("app_id")
    val label = varchar("label", 255).nullable()
    val lang = varchar("lang", 255).nullable()
    val createdAt = long("created_at")
    val updatedAt = long("updated_at")
}

class ContentTypeModel(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<ContentTypeModel>(ContentTypeTable)

    var appId by SkillLevelTable.appId
    var label by ContentTypeTable.label
    var lang by ContentTypeTable.lang
    var updatedAt by ContentTypeTable.updatedAt
    var createdAt by ContentTypeTable.createdAt

    fun toContentType() = ContentType(
        id = id.value,
        appId = appId,
        label = label,
        lang = lang,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

data class ContentType(
    val id: Long? = null,
    val appId: Long? = null,
    val label: String? = null,
    val lang: String? = null,
    val createdAt: Long,
    val updatedAt: Long,
)
