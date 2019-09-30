package poc.spark.sql

import poc.spark.sql.service.SparkSqlService

/**
 * @author ashishb888
 */
object SparkSqlApp {
  def main(args : Array[String]) {
    println( "main service" )
    SparkSqlService.start(args(0))
  }

}
