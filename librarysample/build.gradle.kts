import java.net.URI

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("maven-publish")
}

group = "co.kr.parkjonghun"
version = "0.0.1"

android {
    namespace = "co.kr.parkjonghun.librarysample"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
        targetSdk = 34

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    testImplementation(libs.junit)
}

// Maven publish order
afterEvaluate {
    val sourcesReleaseJar by tasks.registering(Jar::class) {
        archiveClassifier.set("sourcesRelease")
        from(android.sourceSets.getByName("main").java.srcDirs)
    }

    // Publish
    publishing {
        val projectName = name

        // Define target Maven Repository
        repositories {
            maven {
                name = "sonatype"
                url = URI("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")

                credentials {
                    username = "Nexus manager id"
                    password = "Nexus manager pw"
                }
            }
        }

        // Info about the artifact to deploy
        publications {
            register<MavenPublication>("release") {
                from(components["release"])
                artifact(sourcesReleaseJar.get())
                artifactId = projectName
                groupId = group as String
                version = version
            }
        }
    }
}