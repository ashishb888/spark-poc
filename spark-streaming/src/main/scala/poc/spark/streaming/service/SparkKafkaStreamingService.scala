package poc.spark.streaming.service

import java.util.UUID

import org.apache.spark.sql.SparkSession

object SparkKafkaStreamingService {

  def start(topic: String, brokers: String): Unit = {
    // inOutKafka(topic, brokers)
    readJson(topic, brokers)
  }

  def readJson(topic: String, brokers: String): Unit = {
    println("readJson service")

    val spark = SparkSession.builder.appName("Spark Streaming App").getOrCreate()

    import spark.implicits._
    import org.apache.spark.sql.functions.{get_json_object, json_tuple, max}

    val inputStream = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", brokers)
      .option("subscribe", topic)
      .option("startingOffsets", "earliest")
      // .option("minPartitions", "10")
      .option("failOnDataLoss", "true")
      .load()
    inputStream.printSchema

    //    val groupBySeries = inputStream
    //      .select(get_json_object(($"value").cast("string"), "$.SERIES").alias("SERIES"))
    //      .groupBy($"SERIES")
    //      .count()

    val groupByTimestamp = inputStream
      .select(get_json_object(($"value").cast("string"), "$.TIMESTAMP").alias("TIMESTAMP"), get_json_object(($"value").cast("string"), "$.TOTTRDVAL").cast("double").alias("TOTTRDVAL"))
      .groupBy($"TIMESTAMP")
      //.count()
      .agg(max("TOTTRDVAL").alias("MAX TOTTRDVAL"))

    import org.apache.spark.sql.streaming.Trigger

    //    val seriesQuery =
    //      groupBySeries
    //        .writeStream
    //        .format("console")
    //        .outputMode("complete")
    //        .trigger(Trigger.ProcessingTime("20 seconds"))
    //        .start()
    //
    //    seriesQuery.awaitTermination()

    val timestampQuery =
      groupByTimestamp
        .writeStream
        .format("console")
        .outputMode("complete")
        .trigger(Trigger.ProcessingTime("20 seconds"))
        .start()

    timestampQuery.awaitTermination()
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
