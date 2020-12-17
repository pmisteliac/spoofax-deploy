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

gitonium {
  tagPattern = java.util.regex.Pattern.compile("""devenv-release/(.+)""")
}

subprojects {
  configure<mb.gradle.config.MetaborgExtension> {
    configureSubProject()
  }
}

val spoofax2Version: String = System.getProperty("spoofax2Version")
val spoofax2BaselineVersion: String = System.getProperty("spoofax2BaselineVersion")
allprojects {
  group = "org.metaborg.devenv"
  ext["spoofax2Version"] = spoofax2Version
  ext["spoofax2BaselineVersion"] = spoofax2BaselineVersion
}
