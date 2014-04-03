import sbt._
import Keys._
import android.Keys._

object CollabBuild extends Build {

  val androidSettings = Seq(
    platformTarget in Android := "android-19",
    apkbuildExcludes in Android ++= Seq(
      "META-INF/ASL2.0",
      "META-INF/LICENSE",
      "META-INF/NOTICE",
      "reference.conf"),
    useProguard in Android := true,
    proguardCache in Android ++= Seq(
      ProguardCache("org.scaloid") % "org.scaloid"),
    proguardOptions in Android ++= Seq(
      "-dontwarn sun.misc.Unsafe",
      "-dontwarn sun.relfect.Reflection",
      "-dontwarn org.joda.time.**",
      "-dontwarn org.w3c.**",
      "-keep class spray.**",
      "-keep class akka.**",
      "-keep class akka.** { <init>(...); }",
      "-keepclassmembers class akka.**"))

  val defaults = Defaults.defaultSettings ++ Seq(
    name := "collab-android",
    organization := "d01100100",
    version := "0.1",
    scalaVersion := "2.10.3",
    scalacOptions := Seq(
      "-unchecked",
      "-deprecation",
      "-feature",
      "-encoding",
      "utf8"),
    libraryDependencies ++= Seq(
      "com.android.support" %  "support-v4" % "18.0.0",
      "org.scaloid"         %% "scaloid"    % "3.1-8-RC1",
      "d01100100"           %% "colors"     % "0.1",
      "io.spray"            %% "spray-json" % "1.2.5",
      "io.spray"            %  "spray-can"  % "1.2.1",
      "com.typesafe.akka"   %% "akka-actor" % "2.2.3"),
    resolvers ++= Seq(
      "sonatype" at "http://oss.sonatype.org/content/repositories/snapshots/",
      "typesafe" at "http://repo.typesafe.com/typesafe/releases/",
      "spray"    at "http://repo.spray.io/")
  ) ++ android.Plugin.androidBuild ++ androidSettings

  lazy val main = Project(
    id = "collab-android",
    base = file("."),
    settings = defaults)
}
