architectury {
    platformSetupLoomIde()
    fabric()
}

val common: Configuration by configurations.creating
val developmentFabric: Configuration = configurations.getByName("developmentFabric")
val includeTransitive: Configuration = configurations.getByName("includeTransitive")

configurations {
    compileClasspath.get().extendsFrom(configurations["common"])
    runtimeClasspath.get().extendsFrom(configurations["common"])
    developmentFabric.extendsFrom(configurations["common"])
}

tasks {
    remapJar {
        archiveBaseName.set("floodgate-fabric")
    }
}

dependencies {
    modImplementation(libs.fabric.loader)
    modApi(libs.fabric.api)
    common(project(":mod", configuration = "namedElements")) { isTransitive = false }
    shadow(project(path = ":mod", configuration = "transformProductionFabric")) {
        isTransitive = false
    }

    includeTransitive(libs.floodgate.core)
    api(libs.floodgate.core)
    api(libs.guice)

    modImplementation(libs.cloud.fabric)
    include(libs.cloud.fabric)

    modLocalRuntime(libs.geyser.fabric) {
        exclude(group = "io.netty")
        exclude(group = "io.netty.incubator")
    }
}