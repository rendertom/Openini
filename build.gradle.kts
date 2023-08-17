import org.jetbrains.changelog.markdownToHTML

plugins {
    id("java")
    id("org.jetbrains.changelog") version "1.3.1"
    id("org.jetbrains.intellij") version "1.15.0"
}

group = "com.rendertom"
version = "1.1.0"

repositories {
    mavenCentral()
}

intellij {
    version.set("2022.2.5")
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    patchPluginXml {
        sinceBuild.set("222")
        untilBuild.set("232.*")

        pluginDescription.set(extractText("./README.md"))
        changeNotes.set(extractText("./CHANGELOG.md"))
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