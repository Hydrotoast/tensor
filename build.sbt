// Common settings
lazy val commonSettings =
  Seq(
    organization := "com.giocc",
    scalaVersion := "2.11.8",
    crossScalaVersions := Seq("2.10.6", "2.11.8", "2.12.4")
  )

// Git versioning
lazy val versionSettings = 
  Seq(git.baseVersion := "0.0.1")

// Bintray
lazy val publishSettings = 
  Seq(
    licenses += "MIT" -> url("http://opensource.org/licenses/MIT"),
    bintrayPackage := name.value,
    bintrayPackageLabels := Seq("scala", "tensor"),
    bintrayRepository := "tensor"
  )

lazy val disablePublishSettings =
  Seq(
    publish := {}
  )

lazy val root = project
  .in(file("."))
  .enablePlugins(GitVersioning)
  .settings(
    commonSettings ++
    disablePublishSettings ++
    Seq(
      name := "tensor"
    )
  )
  .aggregate(
    core
  )

lazy val core = project
  .settings(
    commonSettings ++
    versionSettings ++
    publishSettings ++
    Seq(
      name := "tensor-core",
      publishArtifact := true,
      publishArtifact in Test := false
    )
  )
