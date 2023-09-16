package com.assessment.fxtradingmultimicroservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="trade")
public class TradeInformation {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	
	@Column(name = "trader_name", nullable = false)
    private String traderName;

    @Column(name = "trade_date", nullable = false)
    private java.sql.Timestamp tradeDate;

    @Column(name = "original_amount", nullable = false)
    private String originalAmount;

    @Column(name = "transferred_amount", nullable = false)
    private String transferredAmount;

    @Column(name = "currency_pair", nullable = false)
    private String currencyPair;
    
        
    @Column(name = "exchange_rate", nullable = false)
    private String exchangeRateValue; 
    
    
    public TradeInformation() {
    	
    }
    
    public TradeInformation(String traderName, String originalAmount, String transferredAmount, String currencyPair) {
		super();
		this.traderName = traderName;
		this.originalAmount = originalAmount;
		this.transferredAmount = transferredAmount;
		this.currencyPair = currencyPair;
		this.tradeDate = new java.sql.Timestamp(System.currentTimeMillis());
	}

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTraderName() {
		return traderName;
	}

	public void setTraderName(String traderName) {
		this.traderName = traderName;
	}

	public java.sql.Timestamp getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(java.sql.Timestamp tradeDate) {
		this.tradeDate = tradeDate;
	}

	public String getOriginalAmount() {
		return originalAmount;
	}

	public void setOriginalAmount(String originalAmount) {
		this.originalAmount = originalAmount;
	}

	public String getTransferredAmount() {
		return transferredAmount;
	}

	public void setTransferredAmount(String transferredAmount) {
		this.transferredAmount = transferredAmount;
	}

	public String getCurrencyPair() {
		return currencyPair;
	}

	public void setCurrencyPair(String currencyPair) {
		this.currencyPair = currencyPair;
	}

	
	public String getExchangeRateValue() {
		return exchangeRateValue;
	}

	public void setExchangeRateValue(String exchangeRateValue) {
		this.exchangeRateValue = exchangeRateValue;
	}
	
	@Override
	public String toString() {
		return "TradeInformation [id=" + id + ", traderName=" + traderName + ", tradeDate=" + tradeDate
				+ ", originalAmount=" + originalAmount + ", transferredAmount=" + transferredAmount + ", currencyPair="
				+ currencyPair + ", exchangeRateValue=" + exchangeRateValue + "]";
	}






	    
}
