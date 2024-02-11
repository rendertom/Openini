import org.jetbrains.changelog.markdownToHTML

group = "com.rendertom"
version = "1.1.0"

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0") // JUnit Jupiter API for writing tests
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0") // JUnit Jupiter Engine to run the tests

    testImplementation("org.mockito:mockito-core:3.11.2")
    testImplementation("org.mockito:mockito-inline:3.11.2") // For static method mocking
    testImplementation("org.mockito:mockito-junit-jupiter:3.11.2") // Mockito JUnit Jupiter support
}

intellij {
    version.set("2022.2.5")
}

plugins {
    id("java")
    id("org.jetbrains.changelog") version "1.3.1"
    id("org.jetbrains.intellij") version "1.15.0"
}

repositories {
    mavenCentral()
}

tasks {
    patchPluginXml {
        sinceBuild.set("222")
        untilBuild.set("232.*")

        pluginDescription.set(extractText("./README.md"))
        changeNotes.set(extractText("./CHANGELOG.md"))
    }

    test {
        useJUnitPlatform()
    }

    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
}

fun extractText(filePath: String) = projectDir.resolve(filePath).readText().lines().run {
    val start = "<!-- Plugin info START -->"
    val end = "<!-- Plugin info END -->"

    if (!containsAll(listOf(start, end))) {
        throw GradleException("Plugin info section not found in $filePath file:\n$start ... $end")
    }
    subList(indexOf(start) + 1, indexOf(end))
}.joinToString("\n").run { markdownToHTML(this) }