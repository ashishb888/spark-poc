package poc.spark.csv.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StockTrade implements Serializable {
	private static final long serialVersionUID = 1L;

	private String symbol;
	private String series;
	private double open;
	private double high;
	private double low;
	private double close;
	private double last;
	private double prevClose;
	private long totTrdQty; // Used long for totTrdQty
	private double totTrdVal;
	private String timestamp; // We can use Date/Timestamp for timestamp (07-JAN-2019) instead String
	private double totalTrades;
	private String isin;

//	@JsonProperty("SYMBOL")
//	private String symbol;
//	@JsonProperty("SERIES")
//	private String series;
//	@JsonProperty("OPEN")
//	private double open;
//	@JsonProperty("HIGH")
//	private double high;
//	@JsonProperty("LOW")
//	private double low;
//	@JsonProperty("CLOSE")
//	private double close;
//	@JsonProperty("LAST")
//	private double last;
//	@JsonProperty("PREVCLOSE")
//	private double prevClose;
//	@JsonProperty("TOTTRDQTY")
//	private long totTrdQty; // Used long for totTrdQty
//	@JsonProperty("TOTTRDVAL")
//	private double totTrdVal;
//	@JsonProperty("TIMESTAMP")
//	private String timestamp; // We can use Date/Timestamp for timestamp (07-JAN-2019) instead String
//	@JsonProperty("TOTALTRADES")
//	private double totalTrades;
//	@JsonProperty("ISIN")
//	private String isin;

	public StockTrade() {
		super();
	}

	public StockTrade(String symbol, String series, double open, double high, double low, double close, double last,
			double prevClose, long totTrdQty, double totTrdVal, String timestamp, double totalTrades, String isin) {
		super();
		this.symbol = symbol;
		this.series = series;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.last = last;
		this.prevClose = prevClose;
		this.totTrdQty = totTrdQty;
		this.totTrdVal = totTrdVal;
		this.timestamp = timestamp;
		this.totalTrades = totalTrades;
		this.isin = isin;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public double getClose() {
		return close;
	}

	public void setClose(double close) {
		this.close = close;
	}

	public double getLast() {
		return last;
	}

	public void setLast(double last) {
		this.last = last;
	}

	public double getPrevClose() {
		return prevClose;
	}

	public void setPrevClose(double prevClose) {
		this.prevClose = prevClose;
	}

	public long getTotTrdQty() {
		return totTrdQty;
	}

	public void setTotTrdQty(long totTrdQty) {
		this.totTrdQty = totTrdQty;
	}

	public double getTotTrdVal() {
		return totTrdVal;
	}

	public void setTotTrdVal(double totTrdVal) {
		this.totTrdVal = totTrdVal;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public double getTotalTrades() {
		return totalTrades;
	}

	public void setTotalTrades(double totalTrades) {
		this.totalTrades = totalTrades;
	}

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

}
