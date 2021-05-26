import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.0"
    application
}

group = "com.ruchij"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.sksamuel.hoplite:hoplite-core:1.4.1")
    implementation("com.sksamuel.hoplite:hoplite-hocon:1.4.1")
    implementation("org.flywaydb:flyway-core:7.9.1")
    implementation("com.h2database:h2:1.4.200")
    implementation("org.postgresql:postgresql:42.2.20")
    implementation("ch.qos.logback:logback-classic:1.2.3")
}

application {
    mainClass.set("com.ruchij.migration.MigrationAppKt")
}

distributions {
    main {
        contents {
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}