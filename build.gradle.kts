plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.15.0"
}

group = "com.rendertom"
version = "1.0.0"

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
    }
}
