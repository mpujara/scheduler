import TestLibraries._

libraryDependencies ++= Seq(
  scalaz,
  jodaTime,
  scalaLogging,
  typesafeConfig,
  macwireMacros,
  macwireUtil,
  macwireProxy,
  specs2 % Test
)

