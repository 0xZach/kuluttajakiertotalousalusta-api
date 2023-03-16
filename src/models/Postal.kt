package com.turku.models

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object PostalTable : LongIdTable("postal", "id") {
    val postalCode = varchar("postal_code", 255).nullable()
    val streetName = varchar("street_name", 255).nullable()
    val municipality = varchar("municipality", 255).nullable()
    var lang = varchar("lang", 255).nullable()
    val createdAt = long("created_at")
    val updatedAt = long("updated_at")
}

class PostalModel(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<PostalModel>(PostalTable)

    var postalCode by PostalTable.postalCode
    var streetName by PostalTable.streetName
    var municipality by PostalTable.municipality
    var lang by PostalTable.lang
    var updatedAt by PostalTable.updatedAt
    var createdAt by PostalTable.createdAt

    fun toPostal() = Postal(
        id = id.value,
        postalCode = postalCode,
        streetName = streetName,
        municipality = municipality,
        lang = lang,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

data class Postal(
    val id: Long? = null,
    val postalCode: String? = null,
    val streetName: String? = null,
    val municipality: String? = null,
    val lang: String? = null,
    val createdAt: Long,
    val updatedAt: Long,
)
