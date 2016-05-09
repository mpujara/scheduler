import com.chartboost.build.Compile
import sbt._

sourcesInBase := false

scalaVersion in ThisBuild := "2.11.8"

lazy val `scheduler` = project
 .in(file("."))
 .settings(
  publishArtifact := false
).aggregate(`update-user-activity`, common)

lazy val common = project
  .in(file("common"))
  .settings(Compile.baseSettings())

lazy val `update-user-activity` = project
  .in(file("update-user-activity"))
  .settings(Compile.baseSettings())
  .dependsOn(common)

