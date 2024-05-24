plugins {
    idea
    `java-library`
    jacoco
    id("org.springframework.boot") version "3.2.1" apply false
    id("io.spring.dependency-management") version "1.1.4"
}

val confluentVersion = "7.6.1"
val springCloudVersion = "2023.0.0"
val lombokVersion = "1.18.30"

repositories {
    maven {
        setUrl("https://packages.confluent.io/maven/")
    }
}

dependencyManagement {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}")
    }
}

dependencies {
    api("org.springframework.boot:spring-boot-starter")
    api("org.springframework.kafka:spring-kafka")
    api("io.confluent:kafka-avro-serializer:$confluentVersion")
    api("io.confluent:kafka-schema-registry-client:$confluentVersion")
    api(project(":module:lib:k8s-workshop-avro-schemes"))

    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")

}