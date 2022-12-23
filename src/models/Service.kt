package com.turku.models

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Table.Dual.nullable

object ServiceTable : LongIdTable("services", "id") {
    val appServiceId = long("app_service_id")
    val appHriId = varchar("app_hri_id", 100)
    val appBusinessId = varchar("app_business_id", 100)
    val appServiceTypeId = varchar("app_service_type_id", 100)
    val serviceTypeName = varchar("service_type_name", 100)
    val lang = varchar("lang", 10)
    val name = varchar("name", 255)
    val latitude = double("latitude")
    val longitude = double("longitude")
    val address = varchar("address", 255)
    val url = varchar("url", 255)
    val phone = varchar("phone", 255)
    val email = varchar("email", 255)
    val details = text("details")
    val createdAt = long("created_at")
    val updatedAt = long("updated_at")
}

class ServiceModel(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<ServiceModel>(ServiceTable)

    var appServiceId by ServiceTable.appServiceId
    var appHriId by ServiceTable.appHriId
    var appBusinessId by ServiceTable.appBusinessId
    var appServiceTypeId by ServiceTable.appServiceTypeId
    var serviceTypeName by ServiceTable.serviceTypeName
    var lang by ServiceTable.lang
    var name by ServiceTable.name
    var latitude by ServiceTable.latitude
    var longitude by ServiceTable.longitude
    var address by ServiceTable.address
    var url by ServiceTable.url.nullable()
    var phone by ServiceTable.phone.nullable()
    var email by ServiceTable.email.nullable()
    var details by ServiceTable.details.nullable()
    var updatedAt by ServiceTable.updatedAt
    var createdAt by ServiceTable.createdAt

    fun toService() = Service(
        id = id.value,
        appServiceId = appServiceId,
        appHriId = appHriId,
        appBusinessId = appBusinessId,
        appServiceTypeId = appServiceTypeId,
        serviceTypeName = serviceTypeName,
        lang = lang,
        name = name,
        latitude = latitude,
        longitude = longitude,
        address = address,
        url = url,
        phone = phone,
        email = email,
        details = details,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

data class Service(
    val id: Long? = null,
    val appServiceId: Long,
    val appHriId: String,
    val appBusinessId: String,
    val appServiceTypeId: String,
    val serviceTypeName: String,
    val lang: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val url: String? = null,
    val phone: String? = null,
    val email: String? = null,
    val details: String? = null,
    val createdAt: Long? = null,
    val updatedAt: Long? = null,
)
