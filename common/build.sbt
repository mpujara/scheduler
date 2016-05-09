import TestLibraries._

libraryDependencies ++= Seq(
  scalaz,
  ficus,
  jodaTime,
  scalaLogging,
  logback,
  typesafeConfig,
  macwireMacros,
  macwireUtil,
  macwireProxy,
  specs2 % Test,
  specs2Mock % Test,
  specs2Extra % Test
)


