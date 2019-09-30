package poc.spark.sql.service

import org.apache.spark.sql.SparkSession

object SparkSqlService {

  def sqlTest(path: String): Unit = {
    println("sqlTest service")

    val logFile = "YOUR_SPARK_HOME/README.md" // Should be some file on your system
    val spark = SparkSession.builder.appName("Spark Sql App").getOrCreate()
    val logData = spark.read.textFile(path).cache()
    val numAs = logData.filter(line => line.contains("a")).count()
    val numBs = logData.filter(line => line.contains("b")).count()
    println(s"Lines with a: $numAs, Lines with b: $numBs")
    spark.stop()
  }

  def start(path: String): Unit = {
    println("start service")

    sqlTest(path)
  }
}
