/*
 * Represents a book trade with relevant trade details.
 */

import java.text.DecimalFormat;
import java.util.HashMap;

public class BookTrade {

    private static int uniqueId=1;
    private int tradeNo;
    private String custName;
    private String currencyPair;
    private double amount;
    private static double rate;
    private static HashMap<String,Double> currencyPairList = new HashMap<String,Double>(){{

        put("USDINR",USDINRConversion.getRate());

    }};

    public BookTrade(String custName, String currencyPair, double amount) {
       
        this.custName = custName;
        this.currencyPair = currencyPair.toUpperCase();
        this.amount = amount;
        tradeNo = uniqueId;
        rate = currencyPairList.get(this.currencyPair);
        uniqueId++;
    }

    public int getTradeNo() {
        return tradeNo;
    }

    public String getCustName() {
        return custName;
    }

    public String getCurrencyPair() {
        return currencyPair;
    }

    public double getAmount() {
        return amount;
    }

    public static double getRate() {
        return rate;
    }

    public static void setRate(double rate) {
        BookTrade.rate = rate;
    }


   
   @Override
    public String toString(){

        

        return (tradeNo+" "+currencyPair+" "+custName+" "+new DecimalFormat("###,###.###").format(amount)+" "+rate);
    }


    /**
     * Performs currency conversion based on the given currency pair.
     * 
     * @param currencyPair The currency pair for which to perform conversion
     */
    
     public void currencyConversion(String currencyPair) {

        if(currencyPair.equals("USDINR")){

            this.amount = USDINRConversion.currencyConversion(amount);
        }
    
    }




}
