package com.turku.models

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object SkillLevelTable : LongIdTable("skill_level", "id") {
    val minSkillEN = varchar("min_skill_en", 255).nullable()
    val minSkillFI = varchar("min_skill_fi", 255).nullable()
    val createdAt = long("created_at")
    val updatedAt = long("updated_at")
}

class SkillLevelModel(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<SkillLevelModel>(SkillLevelTable)

    var minSkillEN by SkillLevelTable.minSkillEN
    var minSkillFI by SkillLevelTable.minSkillFI
    var updatedAt by SkillLevelTable.updatedAt
    var createdAt by SkillLevelTable.createdAt

    fun toSkill() = SkillLevel(
        id = id.value,
        minSkillEN = minSkillEN,
        minSkillFI = minSkillFI,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

data class SkillLevel(
    val id: Long? = null,
    val minSkillEN: String? = null,
    val minSkillFI: String? = null,
    val createdAt: Long,
    val updatedAt: Long,
)
