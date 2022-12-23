package com.turku.models

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object CategoryTable : LongIdTable("categories", "id") {
    val appCategoryId = long("app_category_id").nullable()
    val name = varchar("name", 255).nullable()
    val lang = varchar("lang", 255).nullable()
    val icon = varchar("icon", 255).nullable()
    val createdAt = long("created_at")
    val updatedAt = long("updated_at")
}

class CategoryModel(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<CategoryModel>(CategoryTable)

    var appCategoryId by CategoryTable.appCategoryId
    var name by CategoryTable.name
    var lang by CategoryTable.lang
    var icon by CategoryTable.icon
    var updatedAt by CategoryTable.updatedAt
    var createdAt by CategoryTable.createdAt

    fun toCategory() = Category(
        id = id.value,
        appCategoryId = appCategoryId,
        name = name,
        lang = lang,
        icon = icon,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

data class Category(
    val id: Long? = null,
    val appCategoryId: Long? = null,
    val name: String? = null,
    val lang: String? = null,
    val icon: String? = null,
    val createdAt: Long,
    val updatedAt: Long,
)
