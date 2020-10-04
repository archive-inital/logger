import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version Project.kotlinVersion
    `maven-publish`
}

tasks.withType<Wrapper> {
    gradleVersion = Project.gradleVersion
}

group = "org.spectral"
version = Project.version

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
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/spectral-powered/logger")
        }
    }

    publications {
        create<MavenPublication>("logger") {
            groupId = "org.spectral"
            artifactId = "logger"
            version = Project.version
            from(components["java"])
            artifact(sourcesJar.get())
        }
    }
}
