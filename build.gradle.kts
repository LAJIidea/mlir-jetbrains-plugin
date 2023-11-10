plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.0"
    id("org.jetbrains.intellij") version "1.15.0"
    id("org.jetbrains.grammarkit") version "2022.3.2"
}

group = "com.kingkiller"
version = "1.0-SNAPSHOT"

apply(plugin = "org.jetbrains.grammarkit")

//grammarKit {
//    jflexRelease.set(properties("jflexVersion"))
//}


repositories {
    mavenCentral()
}

//sourceSets {
//    main {
//        java {
//            srcDirs 'src/main/gen'
//        }
//    }
//}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2022.2.5")
    type.set("IC") // Target IDE Platform

    plugins.set(listOf(/* Plugin Dependencies */))
}

tasks {

    generateLexer {
        sourceFile.set(file("src/main/grammars/MLIRLexer.flex"))
        targetDir.set("src/gen/com/kingkiller/mlir/lexer")
        targetClass.set("_MLIRLexer")
        purgeOldFiles.set(true)
    }

    generateParser {
        sourceFile.set(file("src/main/grammars/MLIRParser.bnf"))
        targetRoot.set("src/gen")
        pathToParser.set("com/kingkiller/mlir/parser/MLIRParser.java")
        pathToPsiRoot.set("com/kingkiller/mlir/psi")
        purgeOldFiles.set(true)
//        classpath(project(":$grammarKitFakePsiDeps"))
    }

    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
        dependsOn(generateLexer, generateParser)
    }

    patchPluginXml {
        sinceBuild.set("222")
        untilBuild.set("232.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
