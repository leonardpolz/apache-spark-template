ThisBuild / organization := "com.example"
ThisBuild / version := "0.1.1"

// Make sure that the scala version is supported by the spark version -> Here we are using 2.12.19
ThisBuild / scalaVersion := "2.12.19"

// Important: Make sure that the JDK version is compatible with the spark version -> Here we are using JDK 11

// Make sure that the spark version matches the version of the cluster -> Here we are using 3.4.3
lazy val sparkVersion = "3.4.3"

lazy val root = (project in file("."))
  .settings(
    name := "scala-spark-example",
    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-core" % sparkVersion,
      "org.apache.spark" %% "spark-sql" % sparkVersion
    )
  )
