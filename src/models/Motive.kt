package com.turku.models

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object MotiveTable : LongIdTable("motives", "id") {
    val motive = varchar("motive", 255).nullable()
    val motiveCategory = varchar("motive_category", 255).nullable()
    val hits = long("hits").nullable()
    val createdAt = long("created_at")
    val updatedAt = long("updated_at")
}

class MotiveModel(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<MotiveModel>(MotiveTable)

    var motive by MotiveTable.motive
    var motiveCategory by MotiveTable.motiveCategory
    var hits by MotiveTable.hits
    var updatedAt by MotiveTable.updatedAt
    var createdAt by MotiveTable.createdAt

    fun toMotive() = Motive(
        id = id.value,
        motive = motive,
        motiveCategory = motiveCategory,
        hits = hits,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

data class Motive(
    val id: Long? = null,
    val motive: String? = null,
    val motiveCategory: String? = null,
    val hits: Long? = null,
    val createdAt: Long,
    val updatedAt: Long,
)
