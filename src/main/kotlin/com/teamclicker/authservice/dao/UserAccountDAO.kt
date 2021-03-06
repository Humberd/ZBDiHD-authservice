package com.teamclicker.authservice.dao

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.domain.Persistable
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "userAccount")
class UserAccountDAO : Persistable<String> {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    var _id: String? = null

    @NotNull
    @Column(name = "createdAt", nullable = false)
    @CreatedDate
    var createdAt: Date? = null

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true, optional = true)
    @JoinColumn(name = "deletionId", nullable = true)
    var deletion: UserAccountDeletionDAO? = null

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true, optional = true)
    @JoinColumn(name = "emailPasswordAuthId", nullable = true)
    var emailPasswordAuth: EmailPasswordAuthDAO? = null

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true, optional = true)
    @JoinColumn(name = "facebookAuthId", nullable = true)
    var facebookAuth: FacebookAuthDAO? = null

    @ManyToMany()
    @JoinTable(
        name = "userAccountUserRole",
        joinColumns = [JoinColumn(name = "userAccountId")],
        inverseJoinColumns = [JoinColumn(name = "userRoleId")]
    )
    var roles: Set<UserRoleDAO> = emptySet()


    fun isDeleted() = deletion !== null

    override fun getId() = _id

    override fun isNew() = _id === null
}
