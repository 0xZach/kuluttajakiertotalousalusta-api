package com.turku.repositories

import com.turku.DatabaseFactory.dbQuery
import com.turku.models.Logs
import com.turku.models.LogsModel

class LogsRepository {
    suspend fun getById(id: Long): Logs? = dbQuery {
        LogsModel.findById(id)?.toLogs()
    }

    suspend fun getAll(): List<Logs?> = dbQuery {
        LogsModel.all().map { it.toLogs() }
    }

    suspend fun getCount(): Long = dbQuery {
        LogsModel.count()
    }

    suspend fun create(Logs: Logs): Logs = dbQuery {
        LogsModel.new {
            logTimestamp = Logs.logTimestamp!!
            serviceOrTutorial = Logs.serviceOrTutorial!!
            resultId = Logs.resultId!!
            createdAt = System.currentTimeMillis()
            updatedAt = System.currentTimeMillis()
        }.toLogs()
    }

    suspend fun update(Logs: Logs): Logs? = dbQuery {
        if (Logs.id == null) return@dbQuery null
        val data = LogsModel.findById(Logs.id)
        requireNotNull(data) { "No data found for id ${Logs.id}" }

        data.logTimestamp = Logs.logTimestamp
        data.serviceOrTutorial = Logs.serviceOrTutorial
        data.resultId = Logs.resultId!!
        data.updatedAt = System.currentTimeMillis()

        return@dbQuery data.toLogs()
    }

    suspend fun delete(id: Long) = dbQuery {
        LogsModel[id].delete()
    }
}
