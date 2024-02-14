import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    kotlin("plugin.jpa") version "1.9.22"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation(group = "io.jsonwebtoken", name = "jjwt-api", version = "0.12.3")
    implementation(group = "io.jsonwebtoken", name = "jjwt-impl", version = "0.12.3")
    implementation(group = "io.jsonwebtoken", name = "jjwt-jackson", version = "0.12.3")

    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.session:spring-session-data-redis")

    // https://mvnrepository.com/artifact/com.github.ulisesbocchio/jasypt-spring-boot-starter
    implementation(group = "com.github.ulisesbocchio", name = "jasypt-spring-boot-starter", version = "3.0.5")

    // https://mvnrepository.com/artifact/io.github.oshai/kotlin-logging-jvm
    implementation("io.github.oshai:kotlin-logging-jvm:6.0.3")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    runtimeOnly("com.mysql:mysql-connector-j")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    implementation(project(":core"))
    implementation(project(":domain"))
    runtimeOnly("com.h2database:h2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<BootJar> {
    archiveFileName = "app.jar"
}
