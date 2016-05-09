package com.chartboost.build

import sbt.Keys._
import sbt._

/**
 * Compilation settings
 */
object Compile {

  private val scala_version = Seq(scalaVersion := "2.11.8")

  private val defaultJvmVersion: String = "1.8"

  def librarySettings(jvmVer: String = defaultJvmVersion) = baseSettings(jvmVer) ++ scala_version

  /**
   * Settings for: scalac, javac, and "incOptions"
   */
  def baseSettings(
    jvmVer:          String     = defaultJvmVersion,
    fatalWarnings:   Boolean    = false,
    formatOnCompile: Boolean    = true
  ) =
      Seq(
        javacOptions ++= javacSettings(jvmVer),
        incOptions := incOptions.value.withNameHashing(nameHashing = true),
        scalacOptions := scalacOpts(jvmVer, fatalWarnings, logImplicits = false, deadCodeWarning = true),
        scalacOptions in Test := scalacOpts(jvmVer, fatalWarnings, logImplicits = false, deadCodeWarning = false)
      )

  def scalacSettings(
    jvmVer:          String     = defaultJvmVersion,
    fatalWarnings:   Boolean    = false,
    logImplicits:    Boolean    = false,
    deadCodeWarning: Boolean    = true
  ): Seq[Def.Setting[_]] = {

    scalacOptions := scalacOpts(jvmVer, fatalWarnings, logImplicits, deadCodeWarning)
  }

  private def scalacOpts(jvmVer: String, fatalWarnings: Boolean, logImplicits: Boolean, deadCodeWarning: Boolean) = {
    Seq(
      "-optimize",
      "-deprecation",
      "-feature",
      "-unchecked",
      s"-target:jvm-${jvmVer}",
      "-encoding",
      "utf8",
      "-language:postfixOps",
      "-language:implicitConversions",
      "-language:experimental.macros"
    ).addOption(fatalWarnings, "-Xfatal-warnings")
      .addOption(logImplicits, "-Xlog-implicits")
      .addOption(deadCodeWarning, "-Ywarn-dead-code")
  }

  // Used in `scalacSettings`
  private[this] implicit class AddOption(s: Seq[String]) {
    def addOption(doAdd: Boolean, option: String) =
      if (doAdd)
        s :+ option
      else
        s
  }

  def javacSettings(jvmVer: String = defaultJvmVersion): Seq[String] =
    Seq(
      "-source", jvmVer,
      "-target", jvmVer
    )

}

