package com.turku.plugins

import com.typesafe.config.ConfigFactory
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import com.turku.common.SimpleJWT

fun Application.configureAuthentication() {
    val appConfig = HoconApplicationConfig(ConfigFactory.load())
    val simpleJwt = SimpleJWT(appConfig.property("ktor.jwt.secret").getString())

    install(Authentication) {
        jwt {
            verifier(simpleJwt.verifier)
            validate {
                UserIdPrincipal(it.payload.getClaim("name").asString())
            }
        }
    }
}
