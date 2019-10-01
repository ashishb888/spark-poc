package poc.spark.streaming

import poc.spark.streaming.service.SparkStreamingService

object SparkStreamingApp {
  def main(args : Array[String]) {
    println( "main service" )
    if (args.length < 2) {
      System.err.println(s"""
                            |Usage: DirectKafkaWordCount <brokers> <groupId> <topics>
                            |  <topics> is a list of one or more kafka topics to consume from
                            |  <brokers> is a list of one or more Kafka brokers
                            |
        """.stripMargin)
      System.exit(1)
    }

    SparkStreamingService.start(args(0), args(1))
  }
}
