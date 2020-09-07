name := "Getting-Started-With-Spark-Streaming"

version := "0.1"

scalaVersion := "2.12.12"

val sparkVersion = "3.0.0"
val cassandraConnectorVersion = "3.0.0-beta"
val kafkaVersion = "2.3.0"

libraryDependencies ++= Seq(
  // Spark
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion,

  // Kafka
  "org.apache.kafka" %% "kafka" % kafkaVersion,

  // Spark Streaming + Kafka
  "org.apache.spark" % "spark-sql-kafka-0-10_2.12" % sparkVersion,

  // Cassandra
  "com.datastax.spark" % "spark-cassandra-connector_2.12" % cassandraConnectorVersion
)