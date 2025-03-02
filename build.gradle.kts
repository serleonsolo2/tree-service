plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.jooq.jooq-codegen-gradle") version "3.19.20"
}

group = "com.prewave"
version = "0.0.1-SNAPSHOT"
val openApiVersion = "2.8.5"
val jooqVersion = "3.19.20"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("io.projectreactor:reactor-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation(("org.jooq:jooq-postgres-extensions:$jooqVersion"))

    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")
    runtimeOnly("org.postgresql:postgresql")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$openApiVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:postgresql")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.postgresql:postgresql:42.7.5")
        classpath("org.jooq:jooq-codegen:3.19.20")
        classpath("org.jooq:jooq-postgres-extensions:3.19.20")
    }
}

jooq {
    configuration {
        jdbc {
            url = "jdbc:postgresql://localhost:5432/edges_db"
            user = "user"
            password = "password"
        }
        generator {
            name = "org.jooq.codegen.KotlinGenerator"
            database {
                name = "org.jooq.meta.postgres.PostgresDatabase"
                includes = "edge"
                inputSchema = "public"
            }
            target {
                packageName = "com.prewave.tree.jooq"
                directory = "/src/main/kotlin"
            }
            generate {
                isKotlinNotNullPojoAttributes = true
                isKotlinNotNullRecordAttributes = true
            }
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
