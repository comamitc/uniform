name := "uniform"

version := "0.1.0"

scalaVersion := "2.11.2"

resolvers += Classpaths.sbtPluginReleases

libraryDependencies ++= Seq("org.scalatest" % "scalatest_2.11" % "2.2.1" % "test")	

scalacOptions ++= Seq("-optimise", "-feature", "-deprecation")

instrumentSettings
