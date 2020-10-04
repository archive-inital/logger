import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version Project.kotlinVersion
    `maven-publish`
}

group = "org.spectral"
version = "1.0.0"

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    tinylog()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = Project.jvmVersion
}
val sourcesJar by tasks.registering(Jar::class) {
    classifier = "sources"
    from(sourceSets.main.get().allSource)
}

publishing {
    publications {
        create<MavenPublication>("logger") {
            groupId = "org.spectral"
            artifactId = "logger"
            version = Project.version
        }
    }
}
