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
    implementation(project(":migration-app"))
    implementation("com.sksamuel.hoplite:hoplite-core:1.4.1")
    implementation("com.sksamuel.hoplite:hoplite-hocon:1.4.1")
    implementation(platform("org.http4k:http4k-bom:4.9.0.2"))
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-server-netty")
    implementation("org.http4k:http4k-format-jackson:4.9.0.2")
    implementation("joda-time:joda-time:2.10.10")
    implementation("org.jdbi:jdbi3-kotlin:3.20.0")
    implementation("com.h2database:h2:1.4.200")
    implementation("org.postgresql:postgresql:42.2.20")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")
}

application {
    mainClass.set("com.ruchij.api.ApiAppKt")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}