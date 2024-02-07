plugins {
    id("java")
}

group = "net.mpoisv.unicode"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://repo.skriptlang.org/releases")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.github.SkriptLang:Skript:2.8.2")
    compileOnly("org.spigotmc:spigot:1.19.4-R0.1-SNAPSHOT")
}

tasks{
    processResources {
        from(sourceSets.main.get().resources.srcDirs) {
            filesMatching("plugin.yml") {
                expand(
                    "name" to rootProject.name,
                    "version" to version,
                    "main" to "net.mpoisv.unicode",
                )
            }
            duplicatesStrategy = DuplicatesStrategy.INCLUDE
        }
    }

    compileJava {
        options.encoding = "UTF-8"
    }

    test {
        useJUnitPlatform()
    }
}