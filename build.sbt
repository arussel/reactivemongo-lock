name := """reactivemongo-lock"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  //"org.reactivemongo" %% "play2-reactivemongo" % "0.11.11-play24",
  "org.reactivemongo" %% "play2-reactivemongo" % "0.11.9",
  specs2 % Test,
  "de.flapdoodle.embed" % "de.flapdoodle.embed.mongo" % "1.44" % "test"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
