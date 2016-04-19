import sbt.Keys._
import sbt._

object Build extends Build {

  override lazy val settings = super.settings :+ {
    shellPrompt := (s => Project.extract(s).currentProject.id + " > ")
  }

  lazy val root = Project("utils-scala", file("."))
    .settings(
      description := "yangbajing utils for scala",
      version := "0.0.2",
      homepage := Some(new URL("https://github.com/yangbajing/yangbajing-utils-scala")),
      organization := "yangbajing",
      organizationHomepage := Some(new URL("https://github.com/yangbajing/yangbajing-utils-scala")),
      startYear := Some(2016),
      scalaVersion := "2.11.8",
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
      crossScalaVersions := Seq("2.11.8", "2.10.6"),
      resolvers ++= Seq(
        "Local Maven Repository" at "file:///" + Path.userHome.absolutePath + "/.m2/repository",
        "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
        "Sonatype releases" at "http://oss.sonatype.org/content/repositories/releases",
        "Typesafe Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
        "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots"),
      libraryDependencies ++= Seq(
        _json4sJackson % "provided",
        _jodaTime % "provided",
        _akkaActor % "provided",
        _akkaSlf4j % "provided",
        _okhttpLoggingInterceptor % "provided",
        _okhttp % "provided",
        _hikariCP % "provided",
        _h2 % "provided",
        _postgresql % "provided",
        _mysql % "provided",
        _commonsEmail % "provided",
        _bouncycastle,
        _logback,
        _typesafeConfig,
        _scalaLogging,
        _scalatest))

  val _scalaXml = "org.scala-lang.modules" %% "scala-xml" % "1.0.5"
  val _scalatest = "org.scalatest" %% "scalatest" % "2.2.6" % "test"
  val _typesafeConfig = "com.typesafe" % "config" % "1.3.0"
  val _scalaLogging = ("com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2").exclude("org.scala-lang", "scala-library").exclude("org.scala-lang", "scala-reflect")

  val verAkka = "2.3.15"
  val _akkaActor = "com.typesafe.akka" %% "akka-actor" % verAkka
  val _akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % verAkka

  val _json4sJackson = "org.json4s" %% "json4s-jackson" % "3.2.11"
  val _jodaTime = "joda-time" % "joda-time" % "2.9.3"

  val _bouncycastle = "org.bouncycastle" % "bcprov-jdk15on" % "1.52"
  val _logback = "ch.qos.logback" % "logback-classic" % "1.1.3"
  val _okhttp = "com.squareup.okhttp3" % "okhttp" % "3.2.0"
  val _okhttpLoggingInterceptor = "com.squareup.okhttp3" % "logging-interceptor" % "3.2.0"
  val _hikariCP = "com.zaxxer" % "HikariCP" % "2.4.5"
  val _commonsEmail = "org.apache.commons" % "commons-email" % "1.4"
//  val _guava = "com.google.guava" % "guava" % "19.0"

  val _h2 = "com.h2database" % "h2" % "1.4.191"
  val _postgresql = "org.postgresql" % "postgresql" % "9.4.1208.jre7"
  val _mysql = "mysql" % "mysql-connector-java" % "5.1.38"


}

