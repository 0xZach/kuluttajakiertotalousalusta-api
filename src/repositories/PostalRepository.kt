package com.turku.repositories

import com.turku.DatabaseFactory
import com.turku.models.*
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.or

class PostalRepository {
    suspend fun getById(id: Long): Postal? = DatabaseFactory.dbQuery {
        PostalModel.findById(id)?.toPostal()
    }

    suspend fun getAll(): List<Postal?> = DatabaseFactory.dbQuery {
        PostalModel.all().map { it.toPostal() }
    }

    suspend fun getByAddress(postalCode:String, streetName:String): List<Postal?> = DatabaseFactory.dbQuery {
        PostalModel.find {
            (PostalTable.postalCode like "%$postalCode%") or (PostalTable.streetName like "%$streetName%")
        }.map { it.toPostal() }
    }

    suspend fun create(postal: Postal): Postal = DatabaseFactory.dbQuery {
        PostalModel.new {
            postalCode = postal.postalCode!!
            streetName = postal.streetName!!
            municipality = postal.municipality!!
            lang = postal.lang!!
            createdAt = System.currentTimeMillis()
            updatedAt = System.currentTimeMillis()
        }.toPostal()
    }

    suspend fun update(postal: Postal): Postal? = DatabaseFactory.dbQuery {
        if (postal.id == null) return@dbQuery null
        val data = PostalModel.findById(postal.id)
        requireNotNull(data) { "No data found for id ${postal.id}" }

        data.postalCode = postal.postalCode
        data.streetName = postal.streetName
        data.municipality = postal.municipality
        data.lang = postal.lang
        data.updatedAt = System.currentTimeMillis()

        return@dbQuery data.toPostal()
    }

    suspend fun delete(id: Long) = DatabaseFactory.dbQuery {
        PostalModel[id].delete()
    }
}