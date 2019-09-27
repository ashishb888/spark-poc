package poc.spark.kafka;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import poc.spark.csv.StockTradeApp;

public class SimpleStreamApp {
	private static SparkSession spark;

	private static void stream(String topic) {
		System.out.println("stream service");

		Dataset<Row> df = spark.readStream().format("kafka").option("kafka.bootstrap.servers", "localhost:7092")
				.option("subscribe", topic).load();

		df.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)");

		df.show();
	}

	public static void main(String[] args) {
		spark = SparkSession.builder().appName(StockTradeApp.class.getName()).getOrCreate();

		stream(args[0]);
	}
}
