package com.turku.repositories

import com.turku.DatabaseFactory
import com.turku.models.ServiceType
import com.turku.models.ServiceTypeModel
import com.turku.models.ServiceTypeTable

class ServiceTypeRepository {
    suspend fun getById(id: Long): ServiceType? = DatabaseFactory.dbQuery {
        ServiceTypeModel.findById(id)?.toServiceType()
    }

    suspend fun getAll(): List<ServiceType?> = DatabaseFactory.dbQuery {
        ServiceTypeModel.all().map { it.toServiceType() }
    }

    suspend fun getAllByLocale(lang: String): List<ServiceType?> = DatabaseFactory.dbQuery {
        ServiceTypeModel.find { ServiceTypeTable.lang eq lang }.map { it.toServiceType() }
    }

    suspend fun create(serviceType: ServiceType): ServiceType = DatabaseFactory.dbQuery {
        ServiceTypeModel.new() {
            appId = serviceType.id!!
            typeName = serviceType.typeName
            lang = serviceType.lang
            createdAt = System.currentTimeMillis()
            updatedAt = System.currentTimeMillis()
        }.toServiceType()
    }

    suspend fun update(serviceType: ServiceType): ServiceType? = DatabaseFactory.dbQuery {
        if (serviceType.id == null) return@dbQuery null
        val data = ServiceTypeModel.findById(serviceType.id)
        requireNotNull(data) { "No data found for id ${serviceType.id}" }

        data.typeName = serviceType.typeName
        data.lang = serviceType.lang
        data.updatedAt = System.currentTimeMillis()

        return@dbQuery data.toServiceType()
    }

    suspend fun delete(id: Long) = DatabaseFactory.dbQuery {
        ServiceTypeModel[id].delete()
    }
}