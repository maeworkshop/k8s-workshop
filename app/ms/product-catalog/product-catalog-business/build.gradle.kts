import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
    idea
    id("org.sonarqube") version "5.0.0.4638"
    `java-library`
    id("org.springframework.boot") version "3.2.4" apply false
    id("io.spring.dependency-management") version "1.1.4"
}

dependencyManagement {
    imports {
        mavenBom(SpringBootPlugin.BOM_COORDINATES)
    }
}

val mapstructVersion = "1.5.5.Final"
dependencies {
    api(project(":app:ms:product-catalog:product-catalog-dto"))
    api(project(":app:ms:product-catalog:product-catalog-persistence"))
    api("org.mapstruct:mapstruct:$mapstructVersion")
    api("org.springframework.boot:spring-boot-starter")
    annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")
    annotationProcessor("org.projectlombok:lombok")
}