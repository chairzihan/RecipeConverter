plugins {
    id("java")
    kotlin("jvm") version "1.9.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    implementation("org.json:json:20231013")
    testImplementation(kotlin("test"))

    implementation("com.formdev:flatlaf-intellij-themes:3.2.5")
}

tasks.test {
    useJUnitPlatform()
}