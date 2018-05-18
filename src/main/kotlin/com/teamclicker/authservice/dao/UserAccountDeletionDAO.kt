package com.teamclicker.authservice.dao

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.domain.Persistable
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "userAccountDeletion")
class UserAccountDeletionDAO {

    @NotNull
    @Column(name = "createdAt", nullable = false, updatable = false)
    @CreatedDate
    var createdAt: Date? = null
}
