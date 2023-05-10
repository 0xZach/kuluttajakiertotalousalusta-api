package com.turku.models

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object SkillLevelTable : LongIdTable("skill_level", "id") {
    val label = varchar("label", 255).nullable()
    val lang = varchar("lang", 255).nullable()
    val createdAt = long("created_at")
    val updatedAt = long("updated_at")
}

class SkillLevelModel(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<SkillLevelModel>(SkillLevelTable)

    var label by SkillLevelTable.label
    var lang by SkillLevelTable.lang
    var updatedAt by SkillLevelTable.updatedAt
    var createdAt by SkillLevelTable.createdAt

    fun toSkill() = SkillLevel(
        id = id.value,
        label = label,
        lang = lang,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

data class SkillLevel(
    val id: Long? = null,
    val label: String? = null,
    val lang: String? = null,
    val createdAt: Long,
    val updatedAt: Long,
)
