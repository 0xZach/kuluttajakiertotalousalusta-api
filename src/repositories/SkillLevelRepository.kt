package com.turku.repositories

import com.turku.DatabaseFactory
import com.turku.models.SkillLevel
import com.turku.models.SkillLevelModel

class SkillLevelRepository {
    suspend fun getById(id: Long): SkillLevel? = DatabaseFactory.dbQuery {
        SkillLevelModel.findById(id)?.toSkill()
    }

    suspend fun getAll(): List<SkillLevel?> = DatabaseFactory.dbQuery {
        SkillLevelModel.all().map { it.toSkill() }
    }

    suspend fun create(skillLevel: SkillLevel): SkillLevel = DatabaseFactory.dbQuery {
        SkillLevelModel.new {
            minSkillEN = skillLevel.minSkillEN
            minSkillFI = skillLevel.minSkillFI
            createdAt = System.currentTimeMillis()
            updatedAt = System.currentTimeMillis()
        }.toSkill()
    }

    suspend fun update(skillLevel: SkillLevel): SkillLevel? = DatabaseFactory.dbQuery {
        if (skillLevel.id == null) return@dbQuery null
        val data = SkillLevelModel.findById(skillLevel.id)
        requireNotNull(data) { "No data found for id ${skillLevel.id}" }

        data.minSkillEN = skillLevel.minSkillEN
        data.minSkillFI = skillLevel.minSkillFI
        data.updatedAt = System.currentTimeMillis()

        return@dbQuery data.toSkill()
    }

    suspend fun delete(id: Long) = DatabaseFactory.dbQuery {
        SkillLevelModel[id].delete()
    }
}