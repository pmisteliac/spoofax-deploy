// Apply plugin the old way for compatibility with both Gradle 5.6.4 and 6+.
buildscript {
  repositories {
    maven("https://artifacts.metaborg.org/content/groups/public/")
  }
  dependencies {
    classpath("org.metaborg:gradle.config:0.4.2")
  }
}
apply(plugin = "org.metaborg.gradle.config.root-project")

plugins {
  id("org.metaborg.gitonium") version "0.1.4"

  // Set versions for plugins to use, only applying them in subprojects (apply false here).
  id("org.metaborg.spoofax.gradle.langspec") version "0.4.7" apply false
  id("de.set.ecj") version "1.4.1" apply false
}

subprojects {
  configure<mb.gradle.config.MetaborgExtension> {
    configureSubProject()
  }
}

val spoofax2Version = System.getProperty("spoofax2Version")

allprojects {
  // Override version from gitonium, as Spoofax Core uses a different versioning scheme.
  version = spoofax2Version
}
