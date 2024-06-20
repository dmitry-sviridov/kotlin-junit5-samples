plugins {
    kotlin("jvm") version "1.9.23"
}

group = "ru.otus"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.2")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<Test>("tagged-A") {
    useJUnitPlatform {
        includeTags("A")
    }
}

tasks.register<Test>("tagged-B") {
    useJUnitPlatform {
        includeTags("B")
    }
}

kotlin {
    jvmToolchain(17)
}