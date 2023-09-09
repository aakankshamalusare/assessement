package com.example.fxtrading.service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.fxtrading.dto.Trade;

@Service
public class TradeService {

    private static List<Trade> trades = new LinkedList<>();

    /**
     * 
     * Validates a customer name and currency pair, returning a map of validation errors if any.
     * @param custName The customer's name
     * @param currencyPair The currency pair to validate.
     * @param usdAmount The usdAmount
     * @return A map of validation errors (empty if no errors).
     * 
     */
   
    private Map<String,String> isValid(String custName,String currencyPair ,String usdAmount){
       
        Map<String,String> validationError = new HashMap<>();
        
        if(custName.isEmpty())

            validationError.put("Customer","Customer name can't be empty!");

       if(currencyPair.isEmpty()){
                    validationError.put("Currency-Pair","Currency pair can't be empty");
            
        }else{

                if(!currencyPair.equalsIgnoreCase("USDINR"))
                         validationError.put("Currency-Pair","Invalid Currency Pair it must be USDINR");
        }

        if(!usdAmount.isEmpty()){


            Double amount = 0.0;
             try{
                        
                    amount = Double.parseDouble(usdAmount);
                     
                }catch(NumberFormatException ne){

                        validationError.put("USDAmount","Amount must be Integer");
                
                }

                if(amount<0.0){

                     validationError.put("USDAmount","Amount must be Positive");
               
                }

         }else{

            validationError.put("USD Amount","USDAmount can't be empty!");
         }
        
         return validationError;

     }




     

    /**
     * 
     * Checks for missing fields in a Trade object and returns a map of missing field names and error messages.
     * @param trade The Trade object to be checked.
     * @return A map of missing fields and their error messages
     */

     private Map<String,String> CheckMissing(Trade trade){

         Map<String,String> response = new LinkedHashMap<>();
      
        if(trade.getCustName()==null)
                response.put("CustName","Missing Customer name");
            
        if(trade.getCurrencyPair()==null)
                response.put("Currency-Pair","Missing Currency pair");

        if(trade.getUsdAmount()==null)
                response.put("USDAmount","Missing USD amount");

        return response;



     }



    
   
    /**
     * 
     * Books a trade, validates trade data, and returns a response with booking status or validation errors.
     * @param trade The Trade object to be booked and validated.
     * @return  ResponseEntity containing booking status or validation errors.
     * 
     */

   public ResponseEntity<Map<String,String>> bookTrade(Trade trade) {


        Map<String,String> response = new LinkedHashMap<>();
      
        response = CheckMissing(trade);

        if(!(response.isEmpty())){

                response.put("Message","Trade not booked");
               
                return ResponseEntity.badRequest().body(response);
        } 
        
         Double amount  = 0.0;

        response = isValid(trade.getCustName(),trade.getCurrencyPair(),trade.getUsdAmount());
        
         if(response.isEmpty()){


                    amount=new Double(trade.getUsdAmount());

                    Double finalAmount = amount*trade.getRate();
                    String indAmount = new DecimalFormat("###,###.##").format(finalAmount);
                    String usdAmount = new DecimalFormat("###,###.##").format(amount);

                    trade.setIndAmount(indAmount);
                    trade.setUsdAmount(usdAmount);
                    trade.setCurrencyPair(trade.getCurrencyPair().toUpperCase());

                    Trade booTrade = new Trade(trade.getCustName(), trade.getCurrencyPair(), trade.getUsdAmount(), trade.getIndAmount());

                    
                     trades.add(booTrade);

            }
         

       if(response.isEmpty()){

            response.put("Message", "Trade for " + trade.getCurrencyPair() + " has been booked with rate " + trade.getRate() + ". The amount of Rs " + trade.getIndAmount() + " will be transferred in 2 working days to " + trade.getCustName());
            return ResponseEntity.ok().body(response);


        }

        response.put("Message","Trade not booked");
        return ResponseEntity.badRequest().body(response);
        

    }



   /**
    * 
    * Retrieves trade information and returns it as a response. If no trade information is available, a bad request response with an error message is returned.
    * @return ResponseEntity containing trade information or an error message
    
    */
   
    public ResponseEntity<Object> getTrades() {
        
        if(trades.isEmpty()){

            return ResponseEntity.badRequest().body("No Trade Information available");
        }

        return ResponseEntity.ok().body(trades);
    }
    
}
