plugins {
    kotlin("jvm") version "2.1.21"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Dropwizard Core
    implementation("io.dropwizard:dropwizard-core:4.0.15")
    // Kotlin Standard Library
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.1.21")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.16.1")
    testImplementation(kotlin("stdlib"))
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
    implementation("ch.qos.logback:logback-classic:1.4.14")

    implementation("org.postgresql:postgresql:42.7.3")
    implementation("io.dropwizard:dropwizard-db:4.0.15")
    implementation("io.dropwizard:dropwizard-jdbi3:4.0.15")
    // database connectivity
    implementation("org.jdbi:jdbi3-core:3.45.0") // basic jdbi library
    implementation("org.jdbi:jdbi3-kotlin:3.45.0") //jdbi and kotlin integration
    implementation("org.jdbi:jdbi3-sqlobject:3.45.0") //DAO helper
}

application {
    mainClass.set("com.pieq.application.EmployeeApplicationKt") // specifies the main class of the application for gradle to run
    //if jar is executed/ran manually, this application set is not required/not mandatory.
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

tasks.named<JavaExec>("run") {
    args = listOf("server", "src/main/resources/config.yml")

    jvmArgs = listOf("-Duser.timezone=Asia/Kolkata")
}