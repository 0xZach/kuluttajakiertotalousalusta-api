package com.turku.repositories

import com.turku.DatabaseFactory.dbQuery
import com.turku.models.*
import org.jetbrains.exposed.sql.and

class ItemRepository {
    suspend fun getById(id: Long): Item? = dbQuery {
        ItemModel.findById(id)?.toItem()
    }

    suspend fun getItemsByCategory(categoryId: Long): List<Item> = dbQuery {
        ItemModel.find { ItemTable.categoryId eq categoryId }.map { it.toItem() }
    }

    suspend fun getItemsByAppCategory(appCategoryId: Long): List<Item> = dbQuery {
        ItemModel.find { ItemTable.appCategoryId eq appCategoryId }.map { it.toItem() }
    }

    suspend fun getLocalizedItemsByCategory(categoryId: Long, lang: String): List<Item> = dbQuery {
        ItemModel.find { ItemTable.categoryId eq categoryId and (ItemTable.lang eq lang) }.map { it.toItem() }
    }

    suspend fun create(item: Item): Item = dbQuery {
        ItemModel.new {
            appItemId = item.appItemId
            appCategoryId = item.appCategoryId
            name = item.name
            lang = item.lang
            icon = item.icon
            categoryId = item.categoryId
            createdAt = System.currentTimeMillis()
            updatedAt = System.currentTimeMillis()
        }.toItem()
    }

    suspend fun update(item: Item): Item? = dbQuery {
        if (item.id == null) return@dbQuery null
        val data = ItemModel.findById(item.id)
        requireNotNull(data) { "No data found for id ${item.id}" }

        data.appItemId = item.appItemId
        data.appCategoryId = item.appCategoryId
        data.name = item.name
        data.lang = item.lang
        data.icon = item.icon
        data.categoryId = item.categoryId
        data.updatedAt = System.currentTimeMillis()

        return@dbQuery data.toItem()
    }

    suspend fun delete(id: Long) = dbQuery {
        ItemModel[id].delete()
    }
}
