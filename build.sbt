import android.Keys._

android.Plugin.androidBuild

name := "collab-android"

scalaVersion := "2.10.3"

platformTarget in Android := "android-19"

apkbuildExcludes in Android ++= Seq(
  "META-INF/ASL2.0",
  "META-INF/LICENSE",
  "META-INF/NOTICE",
  "reference.conf")

proguardCache in Android ++= Seq(
  ProguardCache("org.scaloid") % "org.scaloid")

proguardOptions in Android ++= Seq(
  "-dontwarn sun.misc.Unsafe",
  "-dontwarn sun.relfect.Reflection",
  "-dontwarn org.joda.time.**",
  "-dontwarn org.w3c.**",
  "-keep class spray.**",
  "-keep class akka.**",
  "-keep class akka.** { <init>(...); }",
  "-keepclassmembers class akka.**")

libraryDependencies ++= Seq(
  "com.android.support" %  "support-v4"       % "18.0.0",
  "org.scaloid"         %% "scaloid"          % "3.1-8-RC1",
  "de.tavendo"          %  "autobahn-android" % "0.5.2-SNAPSHOT",
  "io.spray"            %% "spray-json"       % "1.2.5",
  "io.spray"            %  "spray-can"        % "1.2.1",
  "com.typesafe.akka"   %% "akka-actor"       % "2.2.3")

resolvers ++= Seq(
  "sonatype" at "http://oss.sonatype.org/content/repositories/snapshots/",
  "typesafe" at "http://repo.typesafe.com/typesafe/releases/",
  "spray"    at "http://repo.spray.io/")

scalacOptions in Compile += "-feature"

run <<= run in Android

install <<= install in Android
