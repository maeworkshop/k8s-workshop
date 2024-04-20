plugins {
    `java-library`
    id("com.github.davidmc24.gradle.plugin.avro") version "1.3.0"
}

val avroVersion = "1.11.3"
val lombokVersion = "1.18.30"


dependencies {
    implementation("org.apache.avro:avro:$avroVersion")
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
}