package poc.spark.main.csv

import org.apache.spark.sql.SparkSession

object CsvApp {
  def readCsv(): Unit = {
    val logFile = "/var/tmp/test.csv"
    val spark = SparkSession.builder.appName("Csv App").getOrCreate()
    val logData = spark.read.textFile(logFile).cache()
    val numAs = logData.filter(line => line.contains("a")).count()
    val numBs = logData.filter(line => line.contains("b")).count()
    println(s"Lines with a: $numAs, Lines with b: $numBs")
    spark.stop()
  }

  def main(args: Array[String]): Unit = {
    readCsv();
  }

}
