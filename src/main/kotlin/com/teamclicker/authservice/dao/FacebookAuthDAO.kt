package com.teamclicker.authservice.dao

import org.springframework.data.annotation.CreatedDate
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table(name = "facebookAuth")
class FacebookAuthDAO {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    var id: String? = null

    @NotNull
    @Column(name = "createdAt", nullable = false, updatable = false)
    @CreatedDate
    var createdAt: Date? = null

    @NotBlank
    @Column(name = "token", nullable = false)
    var token: String? = null
}
