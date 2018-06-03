package com.teamclicker.authservice.mappers

import com.teamclicker.authservice.dao.UserAccountDAO
import com.teamclicker.authservice.dto.AccountResponseDTO
import org.springframework.stereotype.Service

@Service
class UserAccountDAOToAccountResponseDTO : AbstractMapper<UserAccountDAO, AccountResponseDTO>() {
    override fun parse(from: UserAccountDAO): AccountResponseDTO {
        return AccountResponseDTO().also {
            it._id = from._id
            it.email = from.emailPasswordAuth?.email
            it.roles = from.roles.map { it.id!! }
            it.deletedAt = if (from.isDeleted()) null else from.deletion?.createdAt
            it.createdAt = from.createdAt
        }
    }
}