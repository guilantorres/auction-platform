package com.personal.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(
    scanBasePackages = [
        "com.personal.app",
        "com.personal.application",
        "com.personal.infrastructure",
    ],
)
@EnableJpaRepositories(basePackages = ["com.personal.infrastructure.db"])
@EntityScan(
    basePackages = ["com.personal.infrastructure.db.entities"]
)
class MainApplication

fun main(args: Array<String>) {
    runApplication<MainApplication>(*args)
}

