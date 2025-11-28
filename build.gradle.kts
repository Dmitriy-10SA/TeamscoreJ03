plugins {
    id("java")
}

group = "ru.teamscore"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    //GSON
    implementation("com.google.code.gson:gson:2.8.9")
}

tasks.test {
    useJUnitPlatform()
}