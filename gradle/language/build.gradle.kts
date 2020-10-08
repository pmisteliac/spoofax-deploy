plugins {
  id("org.metaborg.gradle.config.root-project") version "0.3.21"
  id("org.metaborg.gitonium") version "0.1.3"

  // Set versions for plugins to use, only applying them in subprojects (apply false here).
  id("org.metaborg.gradle.config.kotlin-gradle-plugin") version "0.3.21" apply false
  id("org.metaborg.spoofax.gradle.langspec") version "0.4.4" apply false
  id("de.set.ecj") version "1.4.1" apply false
}

subprojects {
  metaborg {
    configureSubProject()
  }
}

// Get spoofax2Version explicitly via gradle.properties, as project properties are not passed to included builds.
val spoofax2Version = run {
  val path = "../../../gradle.properties"
  val file = rootDir.resolve(path)
  if(file.exists() && file.isFile) {
    val properties = java.util.Properties()
    file.inputStream().buffered().use { inputStream ->
      properties.load(inputStream)
    }
    properties.getProperty("spoofax2Version")
      ?: throw GradleException("Cannot determine Spoofax 2 version: Gradle project property 'spoofax2Version' was not set in '$path'")
  } else {
    throw GradleException("Cannot determine Spoofax 2 version: '$path' file of devenv was also not found")
  }
}

allprojects {
  // Override version from gitonium, as Spoofax Core uses a different versioning scheme.
  // Needs to be kept in sync with spoofax2Version of Spoofax 3 and the Spoofax 2 Gradle plugin.
  version = spoofax2Version
}
