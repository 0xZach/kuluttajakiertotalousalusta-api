package com.turku.repositories

import com.turku.DatabaseFactory.dbQuery
import com.turku.models.Category
import com.turku.models.CategoryModel
import com.turku.models.CategoryTable

class CategoryRepository {
    suspend fun getById(id: Long): Category? = dbQuery {
        CategoryModel.findById(id)?.toCategory()
    }

    suspend fun getAll(): List<Category?> = dbQuery {
        CategoryModel.all().map { it.toCategory() }
    }

    suspend fun getAllByLocale(lang: String): List<Category?> = dbQuery {
        CategoryModel.find { CategoryTable.lang eq lang }.map { it.toCategory() }
    }

    suspend fun create(category: Category): Category = dbQuery {
        CategoryModel.new {
            appCategoryId = category.appCategoryId
            name = category.name
            lang = category.lang
            icon = category.icon
            createdAt = System.currentTimeMillis()
            updatedAt = System.currentTimeMillis()
        }.toCategory()
    }

    suspend fun update(category: Category): Category? = dbQuery {
        if (category.id == null) return@dbQuery null
        val data = CategoryModel.findById(category.id)
        requireNotNull(data) { "No data found for id ${category.id}" }

        data.appCategoryId = category.appCategoryId
        data.name = category.name
        data.lang = category.lang
        data.icon = category.icon
        data.updatedAt = System.currentTimeMillis()

        return@dbQuery data.toCategory()
    }

    suspend fun delete(id: Long) = dbQuery {
        CategoryModel[id].delete()
    }
}
