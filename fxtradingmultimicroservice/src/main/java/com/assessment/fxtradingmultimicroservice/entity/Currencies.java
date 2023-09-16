package com.assessment.fxtradingmultimicroservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "currencies")
public class Currencies {
   
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "currency_pair", nullable = false)
    private String currencyPair;

	@Column(name = "exchange_rate", nullable = false)
    private String exchangeRate;
	
	@Column(name = "currency_symbol",nullable = false)
	private String currencySymbol;
	
	
	public Currencies() {
		
	}
	
	
	public Currencies(String currencyPair, String exchangeRate,String currencySymbol) {
	
		this.currencyPair = currencyPair;
		this.exchangeRate = exchangeRate;
		this.currencySymbol = currencySymbol;
	}


    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCurrencyPair() {
		return currencyPair;
	}

	public void setCurrencyPair(String currencyPair) {
		this.currencyPair = currencyPair;
	}

	public String getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	
	public String getCurrencySymbol() {
		return currencySymbol;
	}


	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}



    
}

