name := """smartdata"""

version := "1.0"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala, PlayAkkaHttpServer)
  .disablePlugins(PlayNettyServer)

scalaVersion := "2.11.8"

val PlayVersion = "2.5.12"
val Elastic4sVersion = "5.2.5"

libraryDependencies ++= Seq(
  "com.evojam" %% "play-elastic4s" % "0.3.1",
  "com.sksamuel.elastic4s" %% "elastic4s-embedded" % Elastic4sVersion % Test,
  "com.typesafe.play" %% "play-specs2" % PlayVersion % Test,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0" % "test"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Workaround of https://github.com/sbt/sbt/issues/2054:
resolvers += Resolver.url("Typesafe Ivy releases", url("https://repo.typesafe.com/typesafe/ivy-releases"))(Resolver.ivyStylePatterns)

routesGenerator := InjectedRoutesGenerator


fork in run := true
