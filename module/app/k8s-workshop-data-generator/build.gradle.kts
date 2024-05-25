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
val testcontainersVersion = "1.19.8"
val commonsCodecVersion = "1.17.0"
val awaitilityVersion = "4.2.1"

tasks {
    withType<JacocoReport> {
        afterEvaluate {
            classDirectories.setFrom(files(classDirectories.files.map {
                fileTree(it) {
                    exclude(listOf("**/config/**/*"))
                }
            }))
        }
    }
}

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
    implementation("io.confluent:kafka-avro-serializer:$confluentVersion")
    implementation("io.confluent:kafka-schema-registry-client:$confluentVersion")
    implementation(project(":module:lib:k8s-workshop-messaging-starter"))

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.assertj:assertj-core")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:testcontainers-bom:$testcontainersVersion")
    testImplementation("org.testcontainers:testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:kafka")
    testImplementation("commons-codec:commons-codec:$commonsCodecVersion")
    testImplementation("org.awaitility:awaitility:$awaitilityVersion")

    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")
}