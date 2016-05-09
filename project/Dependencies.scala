import sbt._

object Dependencies extends Build {

  val scalaz = "org.scalaz" %% "scalaz-core" % "7.1.3"
  val jodaTime = "joda-time" % "joda-time" % "2.8.2"

  /** utils */
  val macwireVersion = "2.1.0"
  val macwireMacros = "com.softwaremill.macwire" %% "macros" % macwireVersion % Provided
  val macwireUtil = "com.softwaremill.macwire" %% "util" % macwireVersion
  val macwireProxy = "com.softwaremill.macwire" %% "proxy" % macwireVersion

  val ficus = "com.iheart" %% "ficus" % "1.2.3"

  val typesafeConfig = "com.typesafe" % "config" % "1.3.0"
  val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0"
  val logback = "ch.qos.logback" % "logback-classic" % "1.1.7"

  /** test libs */
  object TestLibraries {
    val specs2Version = "3.6.5"
    val specs2 = "org.specs2" %% "specs2-core" % specs2Version
    val specs2Mock = "org.specs2" %% "specs2-mock" % specs2Version
    val specs2Extra = "org.specs2" %% "specs2-matcher-extra" % specs2Version
    val scalamockScalatest = "org.scalamock" %% "scalamock-scalatest-support" % "3.2"
    val scalaTest = "org.scalatest" %% "scalatest" % "2.2.1"
    val h2 = "com.h2database" % "h2" % "1.4.187"
  }
}

