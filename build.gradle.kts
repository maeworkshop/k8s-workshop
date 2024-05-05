import org.gradle.plugins.ide.idea.model.IdeaModel

plugins {
    id("org.sonarqube") version "4.4.1.3373"
}

sonar {
    properties {
        property("sonar.projectKey", "maeworkshop_k8s-workshop")
        property("sonar.organization", "maeworkshop")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.coverage.jacoco.xmlReportPaths", "**/build/reports/jacoco/test/jacocoTestReport.xml")
    }
}

subprojects {
    pluginManager.withPlugin("java") {
        val integrationTestSrcPath = "src/integrationTest"
        val integrationTestTaskName = "integrationTest"

        group = "com.maemresen.ecommerce"

        repositories {
            mavenCentral()
        }

        extensions.configure<JavaPluginExtension> {
            sourceCompatibility = JavaVersion.VERSION_21
        }

        extensions.configure<SourceSetContainer> {
            val mainOutput = getByName("main").output
            val testOutput = getByName("test").output
            val integrationTest by creating {
                compileClasspath += mainOutput + testOutput
                runtimeClasspath += mainOutput + testOutput
                java.srcDir("$integrationTestSrcPath/java")
                resources.srcDir("$integrationTestSrcPath/resources")
            }

            configurations {
                getByName("integrationTestImplementation") {
                    extendsFrom(getByName("testImplementation"))
                }

                getByName("integrationTestRuntimeOnly") {
                    extendsFrom(getByName("testRuntimeOnly"))
                }
            }

            tasks {
                register<Test>(integrationTestTaskName) {
                    description = "Runs the integration tests."
                    group = "verification"
                    testClassesDirs = integrationTest.output.classesDirs
                    classpath = integrationTest.runtimeClasspath
                    mustRunAfter("test")
                }

                withType<Test> {
                    useJUnitPlatform()
                }

                named("check") {
                    dependsOn(integrationTestTaskName)
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

                        afterEvaluate {
                            val jacocoExclusions: Set<String>? by project
                            classDirectories.setFrom(files(classDirectories.files.map { dir ->
                                fileTree(dir) {
                                    jacocoExclusions?.let { exclude(it) }
                                }
                            }))
                        }
                    }
                }
            }

            pluginManager.withPlugin("idea") {
                extensions.configure<IdeaModel> {
                    module {
                        testSources.from(integrationTest.java.srcDirs)
                    }
                }
            }
        }
    }
}
