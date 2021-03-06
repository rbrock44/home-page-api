import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.2"
    kotlin("jvm") version "1.5.10"
    kotlin("plugin.spring") version "1.6.10"
}

group = "home"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.6.10"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.10")

    implementation("org.jsoup:jsoup:1.14.3")
    implementation("org.springframework.boot:spring-boot-starter-actuator:2.6.2")
    implementation("org.springframework.boot:spring-boot-starter-web:2.6.2")
    implementation("org.springframework.boot:spring-boot-starter-cache:2.6.2")

    testImplementation(kotlin("test"))
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.6.2") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }

}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}
