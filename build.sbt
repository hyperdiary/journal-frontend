name := """journal-frontend"""
organization := "org.hyperdiary"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "3.3.1"

libraryDependencies ++= Seq(
  guice,
  "org.apache.jena" % "jena-core" % "4.10.0",
  "org.apache.jena" % "jena-arq" % "4.10.0",
  "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.0" % Test)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "org.hyperdiary.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "org.hyperdiary.binders._"
