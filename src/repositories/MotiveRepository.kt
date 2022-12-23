package com.turku.repositories

import com.turku.DatabaseFactory.dbQuery
import com.turku.models.Motive
import com.turku.models.MotiveModel
import com.turku.models.MotiveTable
import org.jetbrains.exposed.sql.Coalesce
import org.jetbrains.exposed.sql.SqlExpressionBuilder.plus
import org.jetbrains.exposed.sql.longLiteral
import org.jetbrains.exposed.sql.update

class MotiveRepository {
    suspend fun getById(id: Long): Motive? = dbQuery {
        MotiveModel.findById(id)?.toMotive()
    }

    suspend fun getMotivesByCategory(category: String): List<Motive?> = dbQuery {
        MotiveModel.find { MotiveTable.motiveCategory eq category }.map { it.toMotive() }
    }

    suspend fun create(newMotive: Motive): Motive = dbQuery {
        MotiveModel.new {
            motive = newMotive.motive
            motiveCategory = newMotive.motiveCategory
            hits = newMotive.hits
            createdAt = System.currentTimeMillis()
            updatedAt = System.currentTimeMillis()
        }.toMotive()
    }

    suspend fun update(motive: Motive): Motive? = dbQuery {
        if (motive.id == null) return@dbQuery null
        val data = MotiveModel.findById(motive.id)
        requireNotNull(data) { "No data found for id ${motive.id}" }

        data.motive = motive.motive
        data.motiveCategory = motive.motiveCategory
        data.hits = motive.hits
        data.updatedAt = System.currentTimeMillis()

        return@dbQuery data.toMotive()
    }

    suspend fun updateHits(motiveIds: List<Long>) = dbQuery {
        MotiveTable.update({ MotiveTable.id inList motiveIds }) {
            it[hits] = Coalesce(hits, longLiteral(0)) + 1
        }
    }

    suspend fun delete(id: Long) = dbQuery {
        MotiveModel[id].delete()
    }
}
