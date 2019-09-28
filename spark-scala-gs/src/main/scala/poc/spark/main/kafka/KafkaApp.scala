package poc.spark.main.kafka

import org.apache.spark.api.java.function.FlatMapFunction
import org.apache.spark.sql.SparkSession
import java.util.UUID

object KafkaApp {

  def simpleStream(): Unit ={
    println("simpleStream service")

    val spark = SparkSession.builder.appName("Kafka App").getOrCreate()

    import spark.implicits._

    val lines = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "sk1")
      .load()
      .selectExpr("CAST(value AS STRING)")
      .as[String]

    // Generate running word count
    val wordCounts = lines.flatMap(_.split(" ")).groupBy("value").count()

    // Start running the query that prints the running counts to the console
    val query = wordCounts.writeStream
      .outputMode("complete")
      .format("console")
      .option("checkpointLocation", "/var/tmp/cp-" + UUID.randomUUID.toString)
      .start()

    query.awaitTermination()
  }

  def main(args: Array[String]): Unit = {
    println("main service")

    simpleStream()
  }
}
