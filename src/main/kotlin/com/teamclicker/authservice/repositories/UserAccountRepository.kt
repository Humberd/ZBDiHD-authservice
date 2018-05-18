package com.teamclicker.authservice.repositories

import com.teamclicker.authservice.dao.UserAccountDAO
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserAccountRepository : MongoRepository<UserAccountDAO, String> {

    @Query(
        """
            {
              'emailPasswordAuth.emailLc': ?0,
              'deletion': null
            }
        """
    )
    fun findByEmail(@Param("emailLc") emailLc: String): Optional<UserAccountDAO>

    @Query(
        value = """
            {
                'emailPasswordAuth.emailLc': ?0,
                'deletion': null
            }
        """,
        exists = true
    )
    fun existsByEmail(@Param("emailLc") emailLc: String): Boolean

    @Query(
        """
            {
                '_id': ?0,
                'deletion': null
            }
        """
    )
    override fun findById(@Param("id") id: String): Optional<UserAccountDAO>

    @Query(
        """
            {
                '_id': ?0
            }
        """
    )
    fun findByIdNoConstraints(@Param("id") id: String): Optional<UserAccountDAO>

    @Query(
        """
            {
                'emailPasswordAuth.passwordReset.token': ?0,
                'emailPasswordAuth.passwordReset.expiresAt': {
                    ${'$'}gte: ?1
                }
            }
        """
    )
    fun findByValidPasswordResetToken(
        @Param("token") token: String,
        @Param("currentDate") currentDate: Date
    ): Optional<UserAccountDAO>

}