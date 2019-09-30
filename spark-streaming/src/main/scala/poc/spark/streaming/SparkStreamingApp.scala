package poc.spark.streaming

import poc.spark.streaming.service.SparkStreamingService

object SparkStreamingApp {
  def main(args : Array[String]) {
    println( "main service" )

    SparkStreamingService.start(args(0))
  }
}
