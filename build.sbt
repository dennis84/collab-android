import android.Keys._

android.Plugin.androidBuild

name := "collab-android"

scalaVersion := "2.10.3"

platformTarget in Android := "android-19"

apkbuildExcludes in Android += "reference.conf"

proguardCache in Android ++= Seq(
  ProguardCache("org.scaloid") % "org.scaloid")

proguardOptions in Android ++= Seq(
  "-dontwarn sun.misc.Unsafe",
  "-dontwarn sun.relfect.Reflection",
  "-keep class spray.**",
  "-keep class akka.**",
  "-keep class akka.** { <init>(...); }",
  "-keepclassmembers class akka.**")

libraryDependencies ++= Seq(
  "org.scaloid"         %% "scaloid"     % "3.1-8-RC1",
  "io.spray"            %% "spray-json"  % "1.2.5",
  "io.spray"            %  "spray-can"   % "1.2.1",
  "com.typesafe.akka"   %% "akka-actor"  % "2.2.3")

resolvers ++= Seq(
  "sonatype" at "http://oss.sonatype.org/content/repositories/snapshots/",
  "typesafe" at "http://repo.typesafe.com/typesafe/releases/",
  "spray"    at "http://repo.spray.io/")

scalacOptions in Compile += "-feature"

run <<= run in Android

install <<= install in Android
