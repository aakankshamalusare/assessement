package com.example.fxtrading.dto;


import javax.validation.constraints.NotBlank;


public class Trade {

    private static Integer uniqueId = 1;
    private Integer tradeNo;
    
    @NotBlank(message = "Name is required.")
    private String custName;
    
   
    @NotBlank(message = "Currencypair is required.")
    private String currencyPair;
    
    @NotBlank(message = "USDAmount is required")
    private String usdAmount;

    @NotBlank(message = "USDAmount is required")
    private String indAmount;

    private Double rate = 66.00;


    public Trade(){

        
    }
    

    public Trade(@NotBlank(message = "Name is required.") String custName,
            @NotBlank(message = "Currencypair is required.") String currencyPair,
            @NotBlank(message = "USDAmount is required") String usdAmount,
            @NotBlank(message = "USDAmount is required") String indAmount) {
        
        tradeNo = uniqueId;
        uniqueId++;
        this.custName = custName;
        this.currencyPair = currencyPair;
        this.usdAmount = usdAmount;
        this.indAmount = indAmount;
    }

    public Integer getTradeNo() {
        return tradeNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
    }

    public String getUsdAmount() {
        return usdAmount;
    }

    public void setUsdAmount(String usdAmount) {
        this.usdAmount = usdAmount;
    }

    public String getIndAmount() {
        return indAmount;
    }

    public void setIndAmount(String indAmount) {
        this.indAmount = indAmount;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }


    @Override
    public String toString() {
        return "Trade [tradeNo=" + tradeNo + ", custName=" + custName + ", currencyPair=" + currencyPair
                + ", usdAmount=" + usdAmount + ", indAmount=" + indAmount + ", rate=" + rate + "]";
    }


    
}
