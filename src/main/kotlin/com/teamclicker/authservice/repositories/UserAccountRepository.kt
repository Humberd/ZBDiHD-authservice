package com.teamclicker.authservice.repositories

import com.teamclicker.authservice.dao.UserAccountDAO
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserAccountRepository : MongoRepository<UserAccountDAO, String> {

    //    @Query(
//        """
//            select user
//            from UserAccountDAO as user
//            where user.emailPasswordAuth.emailLc = :emailLc
//            and user.deletion is null
//        """
//    )
    @Query(
        """
            {
              'emailPasswordAuth.emailLc': ?0,
              'deletion': null
            }
        """
    )
    fun findByEmail(@Param("emailLc") emailLc: String): Optional<UserAccountDAO>

    // https://stackoverflow.com/a/12052390/4256929
//    @Query(
//        """
//            select case when (count (user) > 0) then true else false end
//            from UserAccountDAO as user
//            where user.emailPasswordAuth.emailLc = :emailLc
//            and user.deletion is null
//        """
//    )
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

    //    @Query(
//        """
//            select user
//            from UserAccountDAO as user
//            where user.id = :id
//            and user.deletion is null
//        """
//    )
    @Query(
        """
            {
                'id': ?0,
                'deletion': null
            }
        """
    )
    override fun findById(@Param("id") id: String): Optional<UserAccountDAO>

//    @Query(
//        """
//            select user
//            from UserAccountDAO as user
//            where user.id = :id
//        """
//    )
    @Query(
        """
            {
                'id': ?0
            }
        """
    )
    fun findByIdNoConstraints(@Param("id") id: String): Optional<UserAccountDAO>

//    @Query(
//        """
//            select user
//            from UserAccountDAO as user
//            where user.emailPasswordAuth.passwordReset.token = :token
//            and user.emailPasswordAuth.passwordReset.expiresAt >= :currentDate
//            and user.deletion is null
//        """
//    )
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