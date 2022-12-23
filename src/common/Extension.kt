package com.turku.common

import org.jetbrains.exposed.sql.transactions.TransactionManager
import java.sql.ResultSet

fun <T : Any> String.queryDBAndMap(transform: (ResultSet) -> T): List<T> {
    val result = arrayListOf<T>()
    TransactionManager.current().exec(this) { rs ->
        while (rs.next()) {
            result += transform(rs)
        }
    }
    return result
}

fun String.queryDB() {
    TransactionManager.current().exec(this)
}
