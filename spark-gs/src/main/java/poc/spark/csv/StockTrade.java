package poc.spark.csv;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

@SuppressWarnings("unused")
public class StockTrade {

	private static SparkSession spark;

	private static void sql() {
		System.out.println("sql service");

		Dataset<Row> df = spark.read().format("csv").option("header", true).option("inferSchema", true)
				.load("/var/tmp/files");
		df.printSchema();

		df.createOrReplaceTempView("df_tbl");

		Dataset<Row> result = spark.sql("select timestamp, max(totTrdVal) from df_tbl group by timestamp");

		result.printSchema();
		result.show();
	}

	private static void tranformation() {
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
		spark = SparkSession.builder().appName(StockTrade.class.getName()).getOrCreate();

		// tranformation();
		sql();
	}

}
