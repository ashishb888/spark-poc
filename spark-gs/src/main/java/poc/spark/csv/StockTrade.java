package poc.spark.csv;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class StockTrade {

	private static void start() {
		System.out.println("start service");

		SparkSession spark = SparkSession.builder().appName(StockTrade.class.getName()).getOrCreate();
		Dataset<Row> df = spark.read().format("csv").option("header", true).option("inferSchema", true)
				.load("/var/tmp/files");
		df.printSchema();

		Dataset<Row> result = df.select("timestamp", "totTrdVal").groupBy("timestamp").max("totTrdVal");
		result.printSchema();
		result.show();
	}

	public static void main(String[] args) {
		start();
	}

}
