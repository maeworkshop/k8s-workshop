plugins {
    idea
    java
    jacoco
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
}

val avroVersion = "1.11.3"
val confluentVersion = "7.6.1"
val springCloudVersion = "2023.0.0"
val lombokVersion = "1.18.32"

repositories {
    maven {
        setUrl("https://packages.confluent.io/maven/")
    }
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.apache.avro:avro:$avroVersion")
    implementation(project(":module:lib:k8s-workshop-avro-schemes"))
    implementation(project(":module:lib:k8s-workshop-messaging-spring-boot-starter"))


    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")
}