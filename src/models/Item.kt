package com.turku.models

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object ItemTable : LongIdTable("items", "id") {
    val appItemId = long("app_item_id").nullable()
    val appCategoryId = long("app_category_id").nullable()
    val name = varchar("name", 255).nullable()
    val lang = varchar("lang", 255).nullable()
    val icon = varchar("icon", 255).nullable()
    val categoryId = long("category_id").nullable()
    val createdAt = long("created_at")
    val updatedAt = long("updated_at")
}

class ItemModel(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<ItemModel>(ItemTable)

    var appItemId by ItemTable.appItemId
    var appCategoryId by ItemTable.appCategoryId
    var name by ItemTable.name
    var lang by ItemTable.lang
    var icon by ItemTable.icon
    var categoryId by ItemTable.categoryId
    var updatedAt by ItemTable.updatedAt
    var createdAt by ItemTable.createdAt

    fun toItem() = Item(
        id = id.value,
        appItemId = appItemId,
        appCategoryId = appCategoryId,
        name = name,
        lang = lang,
        icon = icon,
        categoryId = categoryId,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

data class Item(
    val id: Long? = null,
    val appItemId: Long? = null,
    val appCategoryId: Long? = null,
    val name: String? = null,
    val lang: String? = null,
    val icon: String? = null,
    val categoryId: Long? = null,
    val createdAt: Long,
    val updatedAt: Long,
)
