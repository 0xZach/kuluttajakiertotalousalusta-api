package com.turku.repositories

import com.turku.DatabaseFactory.dbQuery
import com.turku.common.queryDBAndMap
import com.turku.models.*

class ServiceRepository {
    suspend fun getById(id: Long): Service? = dbQuery {
        ServiceModel.findById(id)?.toService()
    }

    suspend fun getByProblemServices(problemServices: List<ProblemService>): List<Service> = dbQuery {
        val serviceIds = problemServices.map { it.serviceId!!.toLong() }
        return@dbQuery ServiceModel.find {
            ServiceTable.id inList serviceIds
        }.map { it.toService() }
    }

    suspend fun getProblemServicesByRadius(problemId: Long, lang: String, latitude: String, longitude: String, radius: Int = 20000): List<Service> = dbQuery {
        val query = "SELECT * " +
        "from services as sr join problem_services as ps " +
        "on sr.id = ps.service_id " +
        "where ps.problem_id = $problemId and " +
        "sr.lang = '$lang' and " +
        "ST_DWithin(sr.geom_point, ST_MakePoint($longitude, $latitude)::geography, $radius);"

        return@dbQuery query.queryDBAndMap {
            result -> Service(
                id = result.getString("id").toLong(),
                appHriId = result.getString("app_hri_id"),
                appBusinessId = result.getString("app_business_id"),
                appServiceTypeId = result.getString("app_service_type_id"),
                lang = result.getString("lang"),
                name = result.getString("name"),
                latitude = result.getString("latitude").toDouble(),
                longitude = result.getString("longitude").toDouble(),
                address = result.getString("address"),
                url = result.getString("url"),
                phone = result.getString("phone"),
                email = result.getString("email"),
                details = result.getString("details"),
                createdAt = result.getString("created_at").toLong(),
                updatedAt = result.getString("updated_at").toLong(),
            )
        }
    }

    suspend fun create(result: Service): Service = dbQuery {
        ServiceModel.new {
            appHriId = result.appHriId
            appBusinessId = result.appBusinessId
            appServiceTypeId = result.appServiceTypeId
            lang = result.lang
            name = result.name
            latitude = result.latitude
            longitude = result.longitude
            address = result.address
            email = result.email
            phone = result.phone
            url = result.url
            details = result.details
            createdAt = System.currentTimeMillis()
            updatedAt = System.currentTimeMillis()
        }.toService()
    }

    suspend fun update(result: Service): Service? = dbQuery {
        if (result.id == null) return@dbQuery null
        val data = ServiceModel.findById(result.id)
        requireNotNull(data) { "No data found for id ${result.id}" }
        data.appHriId = result.appHriId
        data.appBusinessId = result.appBusinessId
        data.appServiceTypeId = result.appServiceTypeId
        data.lang = result.lang
        data.name = result.name
        data.latitude = result.latitude
        data.longitude = result.longitude
        data.address = result.address
        data.email = result.email
        data.phone = result.phone
        data.url = result.url
        data.details = result.details
        data.updatedAt = System.currentTimeMillis()

        return@dbQuery data.toService()
    }

    suspend fun delete(id: Long) = dbQuery {
        ServiceModel[id].delete()
    }
}
