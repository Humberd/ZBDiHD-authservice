package com.teamclicker.authservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.config.EnableMongoAuditing


@SpringBootApplication
@EnableMongoAuditing
class AuthserviceApplication

fun main(args: Array<String>) {
    runApplication<AuthserviceApplication>(*args)
}