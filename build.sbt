val jenaVersion    = "6.0.0"
val inruptVersion  = "2.0.0"
val jacksonVersion = "2.14.3"

name := """journal-frontend"""
organization := "org.hyperdiary"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "3.3.1"

libraryDependencies ++= Seq(
  guice,
  "com.github.pureconfig" %% "pureconfig-generic-scala3" % "0.17.10",
  "org.apache.jena"        % "jena-core"                 % jenaVersion,
  "org.apache.jena"        % "jena-arq"                  % jenaVersion,
  "com.inrupt.client"      % "inrupt-client-core"        % inruptVersion,
  "com.inrupt.client"      % "inrupt-client-solid"       % inruptVersion,
  "com.inrupt.client"      % "inrupt-client-webid"       % inruptVersion,
  "com.inrupt.client"      % "inrupt-client-openid"      % inruptVersion,
  "com.inrupt.client"      % "inrupt-client-jena"        % inruptVersion,
  "com.inrupt.client"      % "inrupt-client-okhttp"      % inruptVersion,
  "com.inrupt.client"      % "inrupt-client-jackson"     % inruptVersion,
  // "org.hyperdiary" %% "solid-client-scala" % "0.1.0-SNAPSHOT",
  "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.2" % Test
)

dependencyOverrides ++= Seq(
  "com.fasterxml.jackson.core" % "jackson-annotations" % "2.21",
  "com.fasterxml.jackson.core" % "jackson-core"        % jacksonVersion,
  "com.fasterxml.jackson.core" % "jackson-databind"    % jacksonVersion,
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "org.hyperdiary.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "org.hyperdiary.binders._"
