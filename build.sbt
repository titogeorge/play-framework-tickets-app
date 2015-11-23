name := """angular-seed-play-java"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "org.webjars" % "angularjs" % "1.3.0-beta.2",
  "org.webjars.bower" % "angular-cookies" % "1.4.7",
  "org.webjars" % "requirejs" % "2.1.11-1",
  "org.webjars" % "angular-ui-bootstrap" % "0.14.0",
  "org.webjars" % "ngInfiniteScroll" % "1.2.1",
  "org.mongodb" % "mongo-java-driver" % "3.1.0",
  "org.mongodb.morphia" % "morphia" % "1.0.1",
  "org.mongodb.morphia" % "morphia-logging-slf4j" % "1.0.1",
  "org.mongodb.morphia" % "morphia-validation" % "1.0.1",
  "org.projectlombok" % "lombok" % "1.12.6"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

pipelineStages := Seq(rjs, digest, gzip)


fork in run := true