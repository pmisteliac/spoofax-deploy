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
}

subprojects {
  configure<mb.gradle.config.MetaborgExtension> {
    configureSubProject()
  }
}

val spoofax2Version = System.getProperty("spoofax2Version")

allprojects {
  // Override version from gitonium, as Spoofax Core uses a different versioning scheme. Except for 'spoofax.gradle',
  // since that is released separately.
  if(name != "spoofax.gradle") {
    version = spoofax2Version
  }
}
