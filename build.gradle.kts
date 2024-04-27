import org.gradle.plugins.ide.idea.model.IdeaModel

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
