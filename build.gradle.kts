plugins {
    kotlin("jvm") version "2.2.20" apply false
    kotlin("plugin.spring") version "2.2.20" apply false
    id("org.springframework.boot") version "3.2.0" apply false
    id("io.spring.dependency-management") version "1.1.4" apply false
}

allprojects {
    repositories {
        mavenCentral()
    }
}