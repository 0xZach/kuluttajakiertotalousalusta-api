val ktorVersion: String by project
val logBackClassicVersion: String by project
val exposedVersion: String by project
val postgresql: String by project
val hikariCP: String by project
val flyway: String by project
val quartzScheduler: String by project
val ktorI18n: String by project
val jacksonDataFormat: String by project
val googleApiClient: String by project
val googleOAuthClient: String by project
val apiSheetService: String by project
val proj4Version: String by project
val awsS3: String by project

plugins {
    application
    kotlin( "jvm") version "1.6.21"
    kotlin("plugin.serialization") version "1.6.10"
    id("org.flywaydb.flyway") version "8.3.0"
}

group "com.turku"
version "1.0-SNAPSHOT"


application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://jitpack.io")
    }
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

sourceSets["main"].resources.srcDirs("resources")

flyway {
    url = System.getenv("DB_URL")
    user = System.getenv("DB_USER")
    password = System.getenv("DB_PASSWORD")
    baselineOnMigrate = true
    locations = arrayOf("filesystem:resources/db/migration")
}

dependencies {

    // Ktor
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-serialization:$ktorVersion")
    implementation("io.ktor:ktor-client-jackson:$ktorVersion")
    implementation("io.ktor:ktor-server-auth:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jwt:$ktorVersion")
    implementation("io.ktor:ktor-server-cors:$ktorVersion")
    implementation("io.ktor:ktor-server-call-logging:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-jackson:$ktorVersion")
    implementation("io.ktor:ktor-server-auth:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")


    // Logs
    implementation("ch.qos.logback:logback-classic:$logBackClassicVersion")

    // Exposed ORM
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.postgresql:postgresql:$postgresql")
    implementation("com.zaxxer:HikariCP:$hikariCP")
    implementation("org.flywaydb:flyway-core:$flyway")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-csv:$jacksonDataFormat")

    implementation("com.google.api-client:google-api-client:$googleApiClient")
    implementation("com.google.oauth-client:google-oauth-client-jetty:$googleOAuthClient")
    implementation("com.google.apis:google-api-services-sheets:$apiSheetService")

    // Testing
    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.6.+")
    
    // Localization
    implementation("com.github.aymanizz:ktor-i18n:$ktorI18n")

    // Scheduler
    implementation("org.quartz-scheduler:quartz:$quartzScheduler")

    // Coordinate Utils
    implementation("org.locationtech.proj4j:proj4j:$proj4Version")

    // Aws
    implementation("software.amazon.awssdk:s3:$awsS3")

}