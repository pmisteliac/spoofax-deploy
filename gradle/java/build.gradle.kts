plugins {
  id("org.metaborg.gradle.config.root-project") version "0.3.21"
  id("org.metaborg.gitonium") version "0.1.3"

  // Set versions for plugins to use, only applying them in subprojects (apply false here).
  id("org.metaborg.gradle.config.kotlin-gradle-plugin") version "0.3.21" apply false
  id("org.metaborg.spoofax.gradle.langspec") version "0.4.4" apply false
  id("de.set.ecj") version "1.4.1" apply false
  kotlin("jvm") version "1.3.41" apply false // Use 1.3.41 to keep in sync with embedded Kotlin version of Gradle 5.6.4.
  id("org.gradle.kotlin.kotlin-dsl") version "1.2.9" apply false // Use 1.2.9 to keep in sync with embedded Kotlin version of Gradle 5.6.4.
}

subprojects {
  metaborg {
    configureSubProject()
  }
}

// Get spoofax2Version explicitly via gradle.properties, as project properties are not passed to included builds.
val spoofax2Version = run {
  val propertiesFile = rootDir.resolve("../../../gradle.properties")
  if(propertiesFile.exists() && propertiesFile.isFile) {
    val properties = java.util.Properties()
    propertiesFile.inputStream().buffered().use { inputStream ->
      properties.load(inputStream)
    }
    properties.getProperty("spoofax2Version")!!
  } else {
    null!!
  }
}

allprojects {
  // Override version from gitonium, as Spoofax Core uses a different versioning scheme. Except for 'spoofax.gradle',
  // since that is released separately.
  if(name != "spoofax.gradle") {
    // Needs to be kept in sync with spoofax2Version of Spoofax 3 and the Spoofax 2 Gradle plugin.
    version = spoofax2Version
  }
}
