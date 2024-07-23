architectury {
    platformSetupLoomIde()
    neoForge()
}

provided("com.google.guava", "failureaccess")

val common: Configuration by configurations.creating
// Without this, the mixin config isn't read properly with the runServer neoforge task
val developmentNeoForge: Configuration = configurations.getByName("developmentNeoForge")
val includeTransitive: Configuration = configurations.getByName("includeTransitive")

configurations {
    compileClasspath.get().extendsFrom(configurations["common"])
    runtimeClasspath.get().extendsFrom(configurations["common"])
    developmentNeoForge.extendsFrom(configurations["common"])
}

dependencies {
    // See https://github.com/google/guava/issues/6618
    modules {
        module("com.google.guava:listenablefuture") {
            replacedBy("com.google.guava:guava", "listenablefuture is part of guava")
        }
    }

    neoForge(libs.neoforge)
    common(project(":mod", configuration = "namedElements")) { isTransitive = false }
    shadow(project(path = ":mod", configuration = "transformProductionNeoForge")) { isTransitive = false }

    includeTransitive(libs.floodgate.core)

    implementation(libs.floodgate.core)
    implementation(libs.guice)

    modImplementation(libs.cloud.neoforge)
    include(libs.cloud.neoforge)
}

tasks {
    processResources {
        from(project(":mod").file("src/main/resources/floodgate.accesswidener")) {
            into("/assets/")
        }
    }

    remapJar {
        dependsOn(processResources)
        atAccessWideners.add("floodgate.accesswidener")
        archiveBaseName.set("floodgate-neoforge")
    }

    modrinth {
        loaders.add("neoforge")
    }
}