
ThisBuild / organization := "com.mandelag.akka"
ThisBuild / version := "0.0.1"

lazy val akkaVersion = "2.5.9"
lazy val root = (project in file("."))
  .settings(
    name := "Scratch Akka",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
      "junit" % "junit" % "4.12")
    )

