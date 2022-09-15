

group = "com.narbase"
version = "1.0.0"

val kotlinVersion = "1.7.10"
val coroutinesVersion = "1.6.4"

plugins {
    id("org.jetbrains.kotlin.js")
}

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
}

dependencies {
//    implementation("org.jetbrains.kotlin:kotlin-stdlib-js:$kotlinVersion")
    implementation(project(":dto-web"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:$coroutinesVersion")
    implementation("com.narbase.kunafa:core:0.3.0")
    implementation(npm("material-design-icons-iconfont", "5.0.1"))
    implementation(npm("css-loader", "3.4.2"))
    implementation(npm("style-loader", "1.1.3"))
    implementation(npm("file-loader", "5.0.2"))
    implementation(npm("typeface-open-sans", "0.0.75"))
    implementation(npm("tippy.js", "4.3.4"))
//    implementation(npm("pikaday", "1.8.0"))
    implementation(npm("flatpickr", "4.6.3"))

    implementation(npm("@codemirror/state", "6.1.1"))
    implementation(npm("@codemirror/view", "6.2.3"))
    implementation(npm("@codemirror/commands", "6.1.0"))
//    implementation(npm("@codemirror/language", "6.2.1"))
//    implementation(npm("@codemirror/autocomplete", "6.2.0"))

//    implementation(npm("@lezer/common", "1.0.1"))
//    implementation(npm("@lezer/lr", "1.2.3"))
//    implementation(npm("@lezer/highlight", "1.0.0"))
//    implementation(npm("@lezer/generator", "1.1.1"))

    testImplementation("org.jetbrains.kotlin:kotlin-test-js:$kotlinVersion")

}

kotlin {
    js {
        browser {
            testTask {
//                enabled = false
                useKarma {
//                    useSourceMapSupport()
                    useFirefox()
//                    useChrome()
//                    useChromeHeadless()
                }
            }
        }
        compilations["main"].packageJson {
            customField(
                "resolutions", mapOf(
                    "@codemirror/state" to "6.1.1",
                    "@codemirror/view" to "6.2.3",
                    "@codemirror/commands" to "6.1.0",
                )
            )
        }

        compilations.all {
            kotlinOptions {
                sourceMap = true
                sourceMapEmbedSources = "always"
            }
        }
        binaries.executable()
    }
}