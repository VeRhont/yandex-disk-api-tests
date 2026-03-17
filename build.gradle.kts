plugins {
    kotlin("jvm") version "1.9.23"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testImplementation("io.rest-assured:rest-assured:5.4.0")
    testImplementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.0")
    testImplementation("org.assertj:assertj-core:3.27.7")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(18)
}