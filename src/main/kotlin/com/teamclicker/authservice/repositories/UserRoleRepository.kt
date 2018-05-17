package com.teamclicker.authservice.repositories

import com.teamclicker.authservice.dao.UserRoleDAO
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRoleRepository : MongoRepository<UserRoleDAO, String>