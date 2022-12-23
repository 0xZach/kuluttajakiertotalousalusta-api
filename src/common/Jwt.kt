package com.turku.common

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

class SimpleJWT(secret: String) {
    private val validityInMs = 36_000_00 * 400
    private val algorithm = Algorithm.HMAC256(secret)

    val verifier: JWTVerifier = JWT.require(algorithm).build()
    fun sign(name: String): String = JWT.create()
        .withClaim("name", name)
        .withExpiresAt(getExpiration())
        .sign(algorithm)

    private fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)
}

fun forgotPasswordToken(name: String): String {
    val validityInMs = 24 * 60 * 60
    val algorithm = Algorithm.HMAC256("dAsEohMyPKv4fW2IrXLFcOwG")

    fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)

    return JWT.create()
        .withClaim("name", name)
        .withExpiresAt(getExpiration())
        .sign(algorithm)
}
