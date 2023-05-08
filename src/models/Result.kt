package com.turku.models

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object ResultTable : LongIdTable("results", "id") {
    val appResultId = long("app_result_id").nullable()
    val appProblemId = long("app_problem_id").nullable()
    val appItemId = long("app_item_id").nullable()
    val appCategoryId = long("app_category_id").nullable()
    val appContentTypeId = long("app_content_type_id").nullable()
    val appSkillLevelId = long("app_skill_level_id").nullable()
    val lang = varchar("lang", 255).nullable()
    val tutorialName = varchar("tutorial_name", 255).nullable()
    val tutorialIntro = varchar("tutorial_intro", 255).nullable()
    val tutorialUrl = varchar("tutorial_url", 255).nullable()
    val contentType = varchar("content_type", 255).nullable()
    val minCost = varchar("min_cost", 255).nullable()
    val minSkill = varchar("min_skill", 255).nullable()
    val minTime = varchar("min_time", 255).nullable()
    val tutorialImage = text("tutorial_image").nullable()
    val problemId = long("problem_id").nullable()
    val itemId = long("item_id").nullable()
    val categoryId = long("category_id").nullable()
    val createdAt = long("created_at")
    val updatedAt = long("updated_at")
}

class ResultModel(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<ResultModel>(ResultTable)

    var appResultId by ResultTable.appResultId
    var appProblemId by ResultTable.appProblemId
    var appItemId by ResultTable.appItemId
    var appCategoryId by ResultTable.appCategoryId
    var appContentTypeId by ResultTable.appContentTypeId
    var appSkillLevelId by ResultTable.appSkillLevelId
    var lang by ResultTable.lang
    var tutorialName by ResultTable.tutorialName
    var tutorialIntro by ResultTable.tutorialIntro
    var tutorialUrl by ResultTable.tutorialUrl
    var contentType by ResultTable.contentType
    var minCost by ResultTable.minCost
    var minSkill by ResultTable.minSkill
    var minTime by ResultTable.minTime
    var tutorialImage by ResultTable.tutorialImage
    var problemId by ResultTable.problemId
    var itemId by ResultTable.itemId
    var categoryId by ResultTable.categoryId
    var updatedAt by ResultTable.updatedAt
    var createdAt by ResultTable.createdAt

    fun toResult() = Result(
        id = id.value,
        appResultId = appResultId,
        appProblemId = appProblemId,
        appItemId = appItemId,
        appCategoryId = appCategoryId,
        appContentTypeId = appContentTypeId,
        appSkillLevelId = appSkillLevelId,
        lang = lang,
        tutorialName = tutorialName,
        tutorialIntro = tutorialIntro,
        tutorialUrl = tutorialUrl,
        contentType = contentType,
        minCost = minCost,
        minSkill = minSkill,
        minTime = minTime,
        tutorialImage = tutorialImage,
        problemId = problemId,
        itemId = itemId,
        categoryId = categoryId,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

data class Result(
    val id: Long? = null,
    val appResultId: Long? = null,
    val appProblemId: Long? = null,
    val appItemId: Long? = null,
    val appCategoryId: Long? = null,
    val appContentTypeId: Long? = null,
    val appSkillLevelId: Long? = null,
    val lang: String? = null,
    val tutorialName: String? = null,
    val tutorialUrl: String? = null,
    val tutorialIntro: String? = null,
    val contentType: String? = null,
    val minCost: String? = null,
    val minSkill: String? = null,
    val minTime: String? = null,
    val tutorialImage: String? = null,
    val problemId: Long? = null,
    val itemId: Long? = null,
    val categoryId: Long? = null,
    val createdAt: Long,
    val updatedAt: Long,
)
