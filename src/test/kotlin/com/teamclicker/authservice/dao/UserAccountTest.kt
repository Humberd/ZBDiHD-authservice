@file:Suppress("RemoveRedundantBackticks")

package com.teamclicker.authservice.dao

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*

internal class UserAccountTest {

    @Nested
    inner class isDeleted {
        @Test
        fun `should return true when deletion is not null`() {
            val userAccount = UserAccountDAO().also {
                it.deletion = UserAccountDeletionDAO().also {
                    it.createdAt = Date()
                }
            }

            assertTrue(userAccount.isDeleted())
        }

        @Test
        fun `should return false when deletion is null`() {
            val userAccount = UserAccountDAO().also {
                it.deletion = null
            }

            assertFalse(userAccount.isDeleted())
        }
    }
}