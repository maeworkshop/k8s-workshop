plugins {
    id("org.sonarqube") version "4.4.1.3373"
}

sonar {
    properties {
        property("sonar.projectKey", "maeworkshop_k8s-workshop")
        property("sonar.organization", "maeworkshop")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.verbose", "true")
        property("sonar.coverage.jacoco.xmlReportPaths", "**/build/reports/jacoco/test/jacocoTestReport.xml")
    }
}

subprojects {
    pluginManager.withPlugin("java") {
        group = "com.maemresen.ecommerce"

        repositories {
            mavenCentral()
        }

        extensions.configure<JavaPluginExtension> {
            sourceCompatibility = JavaVersion.VERSION_21
        }

        tasks {
            register<Test>("integrationTest") {
                    description = "Runs the integration tests."
                    group = "verification"
                    mustRunAfter("test")
                useJUnitPlatform {
                    includeTags("itest")
                }
                jvmArgs("-XX:+EnableDynamicAgentLoading")
                }

                withType<Test> {
                    useJUnitPlatform {
                        excludeTags("itest")
                    }
                    jvmArgs("-XX:+EnableDynamicAgentLoading")
                }

                named("check") {
                    dependsOn("integrationTest")
                }
            }

            pluginManager.withPlugin("jacoco") {
                tasks {
                    named("test") {
                        finalizedBy(named("jacocoTestReport"))
                    }

                    named("jacocoTestReport") {
                        dependsOn("test")
                    }

                    withType<JacocoReport> {
                        reports {
                            xml.required = true
                        }
                    }
                }
            }
    }
}
