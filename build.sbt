name := "uniform"

version := "0.1"

scalaVersion := "2.11.2"

libraryDependencies ++= Seq("org.scalatest" % "scalatest_2.11" % "2.2.1" % "test")	

scalacOptions ++= Seq("-optimise", "-feature", "-deprecation")