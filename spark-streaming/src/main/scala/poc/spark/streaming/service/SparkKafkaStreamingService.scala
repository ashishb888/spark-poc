package poc.spark.streaming.service

import java.util.UUID

import org.apache.spark.sql.SparkSession


object SparkKafkaStreamingService {

  def start(topic: String, brokers: String): Unit = {
    inOutKafka(topic, brokers)
  }

  def inOutKafka(topic: String, brokers: String): Unit = {
    println("inOutKafka service")

    val spark = SparkSession.builder.appName("Spark Streaming App").getOrCreate()

    import spark.implicits._

    val lines = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:7092")
      .option("subscribe", topic)
      .load()

    lines.selectExpr("CAST(value AS STRING)")
      .as[String]
    lines.isStreaming
    lines.printSchema

    val write = lines
      .writeStream
      .format("kafka")
      .option("kafka.bootstrap.servers", brokers)
      .option("topic", topic + "-out")
      .option("checkpointLocation", "/var/tmp/cp-" + UUID.randomUUID.toString)
      .start()

    write.awaitTermination()
  }
}
