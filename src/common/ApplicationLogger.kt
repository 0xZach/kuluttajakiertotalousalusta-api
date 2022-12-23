package com.turku.common

import org.slf4j.LoggerFactory


object ApplicationLogger {

    fun app(message: String, data: Any?) {
        val logger = LoggerFactory.getLogger("app")
        logger.info(message + " -- " + data.toString())
    }

    fun app(message: String) {
        val logger = LoggerFactory.getLogger("app")
        logger.info(message)
    }


    fun api(message: String, data: Any?) {
        val logger = LoggerFactory.getLogger("api")
        logger.info(message + " -- " + data.toString())
    }

    fun api(message: String) {
        val logger = LoggerFactory.getLogger("api")
        logger.info(message)
    }


    fun console(message: String, data: Any?) {
        val logger = LoggerFactory.getLogger("console")
        logger.info(message + " -- " + data.toString())
    }

    fun console(message: String) {
        val logger = LoggerFactory.getLogger("console")
        logger.info(message)
    }


}
