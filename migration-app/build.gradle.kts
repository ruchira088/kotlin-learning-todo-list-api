plugins {
    kotlin("jvm") version "1.5.0"
}

group = "com.ruchij"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.flywaydb:flyway-core:7.9.1")
    implementation("com.h2database:h2:1.4.200")
    implementation("org.postgresql:postgresql:42.2.20")
}