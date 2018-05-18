package com.teamclicker.authservice.dto

import java.util.*

class AccountResponseDTO {

    var _id: String? = null

    var createdAt: Date? = null

    var email: String? = null

    var roles: List<String> = emptyList()

    var deletedAt: Date? = null
}