rootProject.name = "spoofax2.releng.root"

pluginManagement {
  repositories {
    maven("https://artifacts.metaborg.org/content/groups/public/")
  }
}

if(org.gradle.util.VersionNumber.parse(gradle.gradleVersion).major < 6) {
  enableFeaturePreview("GRADLE_METADATA")
}


val repoProperties = run {
  val propertiesFile = rootDir.resolve("../../repo.properties")
  if(propertiesFile.exists() && propertiesFile.isFile) {
    val properties = java.util.Properties()
    propertiesFile.inputStream().buffered().use { inputStream ->
      properties.load(inputStream)
    }
    properties
  } else {
    null
  }
}

fun java.util.Properties?.isTrueOrNull(key: String) = this == null || this[key] == "true"

fun String.includeProject(id: String, dir: String = id, path: String = "../../$this/$dir") {
  val projectDir = file(path)
  if(projectDir.exists()) {
    include(id)
    project(":$id").projectDir = projectDir
  }
}


if(repoProperties.isTrueOrNull("jsglr.update")) {
  "jsglr".run {
    includeProject("org.spoofax.jsglr")
    includeProject("org.spoofax.jsglr2")
    includeProject("org.spoofax.interpreter.library.jsglr")
  }
}

if(repoProperties.isTrueOrNull("sdf.update")) {
  "sdf".run {
    includeProject("org.metaborg.parsetable")
    includeProject("sdf2table", "org.metaborg.sdf2table")
    includeProject("org.metaborg.meta.lang.template")
  }
}

if(repoProperties.isTrueOrNull("stratego.update")) {
  "stratego".run {
    includeProject("org.metaborg.meta.lang.stratego")
    includeProject("stratego.build")
    includeProject("stratego.build.spoofax2")
    includeProject("stratego.compiler.pack")
  }
}

if(repoProperties.isTrueOrNull("nabl.update")) {
  "nabl".run {
    includeProject("nabl2.terms")
    includeProject("nabl2.lang")
    includeProject("nabl2.runtime")
    includeProject("nabl2.shared")

    includeProject("statix.solver")
    includeProject("statix.generator")
    includeProject("statix.lang")
    includeProject("statix.runtime")
  }
}

if(repoProperties.isTrueOrNull("spoofax2.update")) {
  "spoofax2".run {
    includeProject("org.metaborg.core")
    includeProject("org.metaborg.core.test")
    includeProject("org.metaborg.spoofax.core")
    includeProject("org.metaborg.meta.core")
    includeProject("org.metaborg.spoofax.meta.core")
    includeProject("meta.lib.spoofax")
  }
}
