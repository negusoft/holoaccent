Release Process
===============

 1. Update `VERSION_CODE` and `VERSION_NAME` in `gradle.properties` file.
 2. Publish: `./gradlew clean build uploadArchives`
 3. Commit: `git commit -am "Prepare version X.Y.Z."`
 4. Tag: `git tag -a X.Y.Z -m "Version X.Y.Z"`
 5. Increment `VERSION_CODE` and `VERSION_NAME` in `gradle.properties` file to next "SNAPSHOT"
 version and append `-SNAPSHOT` to `VERSION_NAME`.
 6. Commit: `git commit -am "Prepare next development version."`
 7. Push: `git push && git push --tags`

Review the [gradle-mvn-push documentation](https://github.com/chrisbanes/gradle-mvn-push) for
details on how to prepare the release.