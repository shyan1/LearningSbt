// A build definition is defined in build.sbt, and it consists of a set of
// projects (of type Project).

// Build-wode settings
ThisBuild / organization := "com.shyan1"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.12.10"

// Common settings
lazy val commonSettings = Seq(
  target := { baseDirectory.value / "target2" }
)

lazy val root = (project in file("."))
  .aggregate(util, core)
  .settings(
    name := "LearningSbt",
    scalacOptions := List("-encoding", "utf8", "-Xfatal-warnings", "-deprecation", "-unchecked"),
    scalacOptions := {
      val old = scalacOptions.value
      scalaBinaryVersion.value match {
        case "2.12" => old
        case _      => old filterNot (Set("-Xfatal-warnings", "-deprecation").apply)
      }
    }
  )

lazy val util = (project in file("util"))
  .dependsOn(bar, baz)              // two dependencies
  .settings(
    commonSettings
  )

// `util` must be updated and compiled before `core` can be compiled.
lazy val core = (project in file("core"))
  .dependsOn(util)
  .settings(
    commonSettings
  )

lazy val bar = (project in file("bar"))

lazy val baz = (project in file("baz"))