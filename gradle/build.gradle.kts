plugins {
  id("org.metaborg.gradle.config.root-project") version "0.3.21"
  id("org.metaborg.gitonium") version "0.1.3"

  // Set versions for plugins to use, only applying them in subprojects (apply false here).
  id("org.metaborg.gradle.config.kotlin-gradle-plugin") version "0.3.21" apply false
  id("org.metaborg.spoofax.gradle.langspec") version "0.4.4" apply false
  id("de.set.ecj") version "1.4.1" apply false
  kotlin("jvm") version "1.3.41" apply false  // Use 1.3.41 to keep in sync with embedded Kotlin version of Gradle 5.6.4.
  id("org.gradle.kotlin.kotlin-dsl") version "1.2.9" apply false
}

subprojects {
  metaborg {
    configureSubProject()
  }
}

allprojects {
  // Override version from gitonium, as Spoofax Core uses a different versioning scheme.
  // Needs to be kept in sync with metaborgVersion of Spoofax 3 and the Spoofax 2 Gradle plugin.
  version = "2.6.0-SNAPSHOT" // HACK: temporarily use 2.6.0-SNAPSHOT
}
