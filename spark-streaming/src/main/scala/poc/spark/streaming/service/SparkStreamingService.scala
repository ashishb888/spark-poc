package poc.spark.streaming.service

import java.util.UUID

import org.apache.spark.sql.SparkSession


object SparkStreamingService {

  def start(topic: String, brokers: String): Unit = {
    // streamingTest(topic)
    SparkKafkaStreamingService.start(topic, brokers);
  }

  def streamingTest(topic: String): Unit = {
    println("streamingTest service")

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
    lines.printSchema()

    val write = lines
      .writeStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:7092")
      .option("topic", topic + "out")
      .option("checkpointLocation", "/var/tmp/cp-" + UUID.randomUUID.toString)
      .start()

    write.awaitTermination()
  }
}
