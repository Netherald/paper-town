plugins {
    kotlin("jvm") version "1.5.21"
}

group = "net.netherald"
version = "1.0-SNAPSHOT"

/* Variables */
val paperVersion : String by project
val pbalanceVersion : String by project
val kommandVersion : String by project
val mysqlVersion : String by project
val hikariCPVersion : String by project

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("net.projecttl:PBalance-api:$pbalanceVersion")
    implementation("io.github.monun:kommand-api:$kommandVersion")
    compileOnly("io.papermc.paper:paper-api:$paperVersion")
    implementation("mysql:mysql-connector-java:$mysqlVersion")
}

val shade = configurations.create("shade")
shade.extendsFrom(configurations.implementation.get())

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "16"
    }
    processResources {
        filesMatching("*.yml") {
            expand(project.properties)
        }
    }
    jar {
        from ( shade.map { if (it.isDirectory) it else zipTree(it) })
    }
}