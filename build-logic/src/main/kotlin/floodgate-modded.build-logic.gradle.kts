repositories {
    // mavenLocal()
    mavenCentral()
    maven("https://maven.fabricmc.net/")
    maven("https://maven.neoforged.net/releases")
    maven("https://repo.opencollab.dev/main/")
    maven("https://jitpack.io") {
        content {
            includeGroupByRegex("com.github.*")
        }
    }
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven("https://prmaven.neoforged.net/NeoForge/pr1076") {
        name = "Maven for 1.21 PR"
        content {
            includeModule("net.neoforged", "neoforge")
        }
    }
}