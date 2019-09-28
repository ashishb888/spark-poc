val sparkVersion = "2.4.3"

lazy val root = (project in file(".")).settings(
  name := "spark-scala-gs",
  version := "0.1",
  scalaVersion := "2.11.12",
  mainClass in Compile := Some("poc.spark.main.kafka.KafkaApp")
)

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-sql-kafka-0-10" % sparkVersion
)

assemblyMergeStrategy in assembly := {
  case PathList("org", "apache", "spark", "unused", "UnusedStubClass.class") =>
    MergeStrategy.first
  case x => (assemblyMergeStrategy in assembly).value(x)
}
