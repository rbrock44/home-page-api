java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

plugins {
    id("org.springframework.boot") version "2.6.2"
    kotlin("jvm") version "1.5.10"
    kotlin("plugin.spring") version "1.6.10"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.0" // If using kotlinx.serialization
}

//baseName = "home-page-api"
group = "com.projects"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("com.h2database:h2:1.4.200")
    runtimeOnly("com.h2database:h2:1.4.200")

    implementation("javax.xml.bind:jaxb-api:2.3.1")
    implementation("org.glassfish.jaxb:jaxb-runtime:2.3.1")
    implementation("javax.activation:activation:1.1.1")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.6.10"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.10")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.10")

    implementation("org.jsoup:jsoup:1.14.3")
    implementation("org.springframework.boot:spring-boot-starter-actuator:2.6.2")
    implementation("org.springframework.boot:spring-boot-starter-web:2.6.2")
    implementation("org.springframework.boot:spring-boot-starter-cache:2.6.2")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.6.2")

    implementation("org.eclipse.jgit:org.eclipse.jgit:6.6.0.202305301015-r")

    testImplementation(kotlin("test"))
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.6.2") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.mockito:mockito-core:4.8.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")
}

tasks.test {
    useJUnit()
}
