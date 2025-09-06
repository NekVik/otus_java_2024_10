import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id ("org.springframework.boot")
    id ("com.github.johnrengelman.shadow")

}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-test")

    implementation ("ch.qos.logback:logback-classic")
    implementation ("org.flywaydb:flyway-core")
    implementation ("org.postgresql:postgresql")
    implementation ("com.google.code.findbugs:jsr305")
    implementation ("org.springframework.boot:spring-boot-starter-data-jdbc")

    runtimeOnly("org.flywaydb:flyway-database-postgresql")

    compileOnly ("org.projectlombok:lombok")
    annotationProcessor ("org.projectlombok:lombok")

    implementation("com.google.code.gson:gson")

}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("HW14-fatjar")
        archiveVersion.set("0.1")
        archiveClassifier.set("")
        manifest {
            attributes(mapOf("Main-Class" to "ru.otus.Application"))
        }
    }

    build {
        dependsOn(shadowJar)
    }
}