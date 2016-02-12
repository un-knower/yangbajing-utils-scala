import sbt.Keys._
import sbt._

object Build extends Build {

  override lazy val settings = super.settings :+ {
    shellPrompt := (s => Project.extract(s).currentProject.id + " > ")
  }

  lazy val root = Project("yangbajing-utils-scala", file("."))
    .settings(
      description := "yangbajing utils for scala",
      version := "0.0.2",
      homepage := Some(new URL("https://github.com/yangbajing/yangbajing-utils-scala")),
      organization := "me.yangbajing",
      organizationHomepage := Some(new URL("https://github.com/yangbajing/yangbajing-utils-scala")),
      startYear := Some(2016),
      scalaVersion := "2.11.7",
      scalacOptions ++= Seq(
        "-encoding", "utf8",
        "-unchecked",
        "-feature",
        "-deprecation"
      ),
      javacOptions ++= Seq(
        "-encoding", "utf8",
        "-Xlint:unchecked",
        "-Xlint:deprecation"
      ),
      offline := true,
      fork := true,
      resolvers ++= Seq(
        "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
        "Sonatype releases" at "http://oss.sonatype.org/content/repositories/releases",
        "Typesafe Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
        "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots"),
      libraryDependencies ++= Seq(
        _mongoScala % "provided",
        _json4sJackson % "provided",
        _redisclient % "provided",
        _akkaHttp % "provided",
        _akkaActor % "provided",
        _akkaSlf4j % "provided",
        _bouncycastle,
        _logback,
        _typesafeConfig,
        _scalaLogging,
        _scalatest))

  val _scalaXml = "org.scala-lang.modules" %% "scala-xml" % "1.0.5"
  val _scalatest = "org.scalatest" %% "scalatest" % "2.2.6" % "test"
  val _typesafeConfig = "com.typesafe" % "config" % "1.3.0"
  val _scalaLogging = ("com.typesafe.scala-logging" %% "scala-logging" % "3.1.0").exclude("org.scala-lang", "scala-library").exclude("org.scala-lang", "scala-reflect")

  val verAkkaHttp = "2.0.3"
  val _akkaStream = "com.typesafe.akka" %% "akka-stream-experimental" % verAkkaHttp
  val _akkaHttpCore = "com.typesafe.akka" %% "akka-http-core-experimental" % verAkkaHttp
  val _akkaHttp = "com.typesafe.akka" %% "akka-http-experimental" % verAkkaHttp

  val verAkka = "2.3.14"
  val _akkaActor = "com.typesafe.akka" %% "akka-actor" % verAkka
  val _akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % verAkka

  val _redisclient = "net.debasishg" %% "redisclient" % "3.1"

  val _json4sJackson = "org.json4s" %% "json4s-jackson" % "3.3.0"

  val _mongoScala = "org.mongodb.scala" %% "mongo-scala-driver" % "1.1.0"

  val _bouncycastle = "org.bouncycastle" % "bcprov-jdk15on" % "1.52"
  val _logback = "ch.qos.logback" % "logback-classic" % "1.1.3"
  val _commonsEmail = "org.apache.commons" % "commons-email" % "1.4"
  val _guava = "com.google.guava" % "guava" % "19.0"

}

