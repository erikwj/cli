
name := "commandlinetools"

organization := "com.faqtfinding"

version := "0.1-SNAPSHOT"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest"  % "2.2.2" % "test"
  ,"org.scalaz" %% "scalaz-core" % "7.1.0"
)     

scalaVersion := "2.11.4"

scalacOptions ++= Seq("-unchecked", "-deprecation","-feature")