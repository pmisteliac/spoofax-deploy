plugins {
  id("org.metaborg.gradle.config.root-project") version "0.3.21"
  id("org.metaborg.gitonium") version "0.1.3"

  // Set versions for plugins to use, only applying them in subprojects (apply false here).
  id("org.metaborg.gradle.config.java-library") version "0.3.21" apply false
  id("org.metaborg.gradle.config.junit-testing") version "0.3.21" apply false
}

subprojects {
  metaborg {
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
