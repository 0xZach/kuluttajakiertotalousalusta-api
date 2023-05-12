package com.turku.repositories

import com.turku.DatabaseFactory
import com.turku.models.*

class ContentTypeRepository {
    suspend fun getById(id: Long): ContentType? = DatabaseFactory.dbQuery {
        ContentTypeModel.find { ContentTypeTable.appId eq id }.map { it.toContentType() }[0]  // TO DO: GO BACK TO NORMAL ID WHEN FIXED DUPLICATION
    }

    suspend fun getAll(): List<ContentType?> = DatabaseFactory.dbQuery {
        ContentTypeModel.all().map { it.toContentType() }
    }

    suspend fun create(contentType: ContentType): ContentType = DatabaseFactory.dbQuery {
        ContentTypeModel.new {
            appId = contentType.appId!!
            label = contentType.label
            lang = contentType.lang
            createdAt = System.currentTimeMillis()
            updatedAt = System.currentTimeMillis()
        }.toContentType()
    }

    suspend fun update(contentType: ContentType): ContentType? = DatabaseFactory.dbQuery {
        if (contentType.id == null) return@dbQuery null
        val data = ContentTypeModel.findById(contentType.id)
        requireNotNull(data) { "No data found for id ${contentType.id}" }

        data.label = contentType.label
        data.lang = contentType.lang
        data.updatedAt = System.currentTimeMillis()

        return@dbQuery data.toContentType()
    }

    suspend fun delete(id: Long) = DatabaseFactory.dbQuery {
        ContentTypeModel[id].delete()
    }
}