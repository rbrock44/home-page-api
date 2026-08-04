plugins {
    id("org.springframework.boot") version "4.1.0"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("jvm") version "2.4.0"
    kotlin("plugin.spring") version "2.4.0"
    kotlin("plugin.jpa") version "2.4.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.4.0"
}

group = "com.projects"
version = "1.0.0"

kotlin {
    jvmToolchain(21)
}

repositories {
    mavenCentral()
}

dependencies {
    runtimeOnly("com.h2database:h2")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.11.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("org.jsoup:jsoup:1.23.1")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    testImplementation(kotlin("test"))
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.mockito:mockito-core:5.23.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:6.3.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.23.0")
}

tasks.test {
    useJUnitPlatform()
}
