package com.turku.repositories

import com.turku.DatabaseFactory
import com.turku.models.*
import org.jetbrains.exposed.sql.and

class SkillLevelRepository {
    suspend fun getLocalizedById(id: Long, lang: String): SkillLevel = DatabaseFactory.dbQuery {
        SkillLevelModel.find { SkillLevelTable.appId eq id and (SkillLevelTable.lang eq lang) }.map { it.toSkill() }[0]  // TO DO: GO BACK TO NORMAL ID WHEN FIXED DUPLICATION
    }

    suspend fun getAll(): List<SkillLevel?> = DatabaseFactory.dbQuery {
        SkillLevelModel.all().map { it.toSkill() }
    }

    suspend fun getAllByLocale(lang: String): List<SkillLevel?> = DatabaseFactory.dbQuery {
        SkillLevelModel.find { SkillLevelTable.lang eq lang }.map { it.toSkill() }
    }

    suspend fun create(skillLevel: SkillLevel): SkillLevel = DatabaseFactory.dbQuery {
        SkillLevelModel.new {
            appId = skillLevel.appId!!
            label = skillLevel.label
            lang = skillLevel.lang
            createdAt = System.currentTimeMillis()
            updatedAt = System.currentTimeMillis()
        }.toSkill()
    }

    suspend fun update(skillLevel: SkillLevel): SkillLevel? = DatabaseFactory.dbQuery {
        if (skillLevel.id == null) return@dbQuery null
        val data = SkillLevelModel.findById(skillLevel.id)
        requireNotNull(data) { "No data found for id ${skillLevel.id}" }

        data.label = skillLevel.label
        data.lang = skillLevel.lang
        data.updatedAt = System.currentTimeMillis()

        return@dbQuery data.toSkill()
    }

    suspend fun delete(id: Long) = DatabaseFactory.dbQuery {
        SkillLevelModel[id].delete()
    }
}