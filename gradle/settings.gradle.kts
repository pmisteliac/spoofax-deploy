rootProject.name = "spoofax2.releng.root"

pluginManagement {
  repositories {
    maven("https://artifacts.metaborg.org/content/groups/public/")
  }
}

if(org.gradle.util.VersionNumber.parse(gradle.gradleVersion).major < 6) {
  enableFeaturePreview("GRADLE_METADATA")
}


// Only include composite builds when this is the root project (it has no parent). Otherwise, the parent project
// (devenv) will include these composite builds, as IntelliJ does not support nested composite builds.
if(gradle.parent == null) {
  includeBuildWithName("java", "spoofax2.releng.java.root")
  includeBuildWithName("language", "spoofax2.releng.language.root")
}

fun includeBuildWithName(dir: String, name: String) {
  includeBuild(dir) {
    try {
      ConfigurableIncludedBuild::class.java
        .getDeclaredMethod("setName", String::class.java)
        .invoke(this, name)
    } catch(e: NoSuchMethodException) {
      // Running Gradle < 6, no need to set the name, ignore.
    }
  }
}
