package poc.spark.csv;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;

import poc.spark.csv.domain.StockTrade;

@SuppressWarnings("unused")
public class StockTradeApp {

	private static SparkSession spark;

	private static void schemaDS() {
		System.out.println("schemaDS service");

		Encoder<StockTrade> stockTradeEncoder = Encoders.bean(StockTrade.class);

		Dataset<StockTrade> ds = spark.read().schema(stockTradeEncoder.schema()).option("inferSchema", false)
				.option("header", true).format("csv").load("/var/tmp/files").as(stockTradeEncoder);
		ds.printSchema();
		ds.show();

		Dataset<Row> result = ds.select("timestamp", "totTrdVal").groupBy("timestamp").max("totTrdVal");
		result.printSchema();
		result.show();
	}

	private static void schemaDF() {
		System.out.println("schemaDF service");

		StructType schema = Encoders.bean(StockTrade.class).schema();
		schema.printTreeString();

		Encoder<StockTrade> stockTradeEncoder = Encoders.bean(StockTrade.class);
		stockTradeEncoder.schema().printTreeString();

		Dataset<Row> df = spark.read().schema(stockTradeEncoder.schema()).option("inferSchema", false)
				.option("header", true).format("csv").load("/var/tmp/files");
		df.printSchema();

		Dataset<Row> result = df.select("timestamp", "totTrdVal").groupBy("timestamp").max("totTrdVal");
		result.printSchema();
		result.show();
	}

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

		Dataset<Row> df = spark.read().format("csv").option("header", true).option("inferSchema", true)
				.load("/var/tmp/files");
		df.printSchema();

		Dataset<Row> result = df.select("timestamp", "totTrdVal").groupBy("timestamp").max("totTrdVal");
		result.printSchema();
		result.show();
	}

	public static void main(String[] args) {
		spark = SparkSession.builder().appName(StockTradeApp.class.getName()).getOrCreate();

		// tranformation();
		// sql();
		schemaDF();
		// schemaDS();
	}

}
