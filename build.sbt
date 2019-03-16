enablePlugins(PlayScala)

name := """play-scala-slick-example"""

version := "2.7.x"

scalaVersion := "2.12.8"

libraryDependencies += guice
libraryDependencies += "com.typesafe.play" %% "play-slick" % "4.0.0"
libraryDependencies += specs2 % Test

scalacOptions ++= Seq(
  "-feature",
  "-deprecation",
  "-Xfatal-warnings"
)
