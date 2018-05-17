package com.teamclicker.authservice.mappers

import com.teamclicker.authservice.dao.AuthenticationMethod
import com.teamclicker.authservice.security.JWTData
import io.jsonwebtoken.Claims
import io.jsonwebtoken.RequiredTypeException
import org.springframework.stereotype.Service

@Service
class ClaimsToJWTDataMapper : AbstractMapper<Claims, JWTData>() {
    override fun parse(from: Claims): JWTData {
        return JWTData(
            accountId = getAccountId(from),
            authenticationMethod = from.get("authenticationMethod", String::class.java)
                .let { AuthenticationMethod.valueOf(it) },
            roles = from.get("roles", userRolesListType).toSet()
        )
    }

    private fun getAccountId(from: Claims): String {
        return try {
            from.get("accountId", String::class.java)
        } catch (e: RequiredTypeException) {
            from.get("accountId", String::class.java)
        }
    }

    companion object {
        val userRolesListType = arrayListOf<String>()::class.java
    }
}