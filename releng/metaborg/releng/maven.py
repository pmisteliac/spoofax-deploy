from mavenpy.settings import MavenSettingsGenerator


class MetaborgMavenSettingsGeneratorGenerator(MavenSettingsGenerator):
  defaultSettingsLocation = MavenSettingsGenerator.user_settings_location()
  defaultReleases = 'https://artifacts.metaborg.org/content/repositories/releases/'
  defaultSnapshots = 'https://artifacts.metaborg.org/content/repositories/snapshots/'
  defaultMirror = 'https://artifacts.metaborg.org/content/repositories/central/'

  def __init__(self, location=defaultSettingsLocation, metaborgReleases=defaultReleases,
      metaborgSnapshots=defaultSnapshots, centralMirror=defaultMirror):
    repositories = []
    if metaborgReleases:
      repositories.append(
        ('add-metaborg-release-repos', 'metaborg-release-repo', metaborgReleases, None, True, False, True))
    if metaborgSnapshots:
      repositories.append(
        ('add-metaborg-snapshot-repos', 'metaborg-snapshot-repo', metaborgSnapshots, None, False, True, True))

    mirrors = []
    if centralMirror:
      mirrors.append(('metaborg-central-mirror', centralMirror, 'central'))

    MavenSettingsGenerator.__init__(self, location=location, repositories=repositories, mirrors=mirrors)
