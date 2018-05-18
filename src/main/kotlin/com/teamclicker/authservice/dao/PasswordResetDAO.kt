package com.teamclicker.authservice.dao

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.domain.Persistable
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.CompoundIndexes
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(
    name = "passwordReset"
)
@CompoundIndexes(
    CompoundIndex(name="validTokenIndex", def="{'expiresAt': 1, 'token': 1}")
)
class PasswordResetDAO{
    @NotNull
    @Column(name = "createdAt", nullable = false)
    @CreatedDate
    var createdAt: Date? = null

    @NotNull
    @Column(name = "expiresAt", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    var expiresAt: Date? = null

    @NotNull
    @Column(name = "token", nullable = false)
    var token: String? = null
}