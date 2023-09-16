package com.assessment.fxtradingmultimicroservice.service;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.assessment.fxtradingmultimicroservice.FxtradingmultimicroserviceApplication;
import com.assessment.fxtradingmultimicroservice.dao.TradeDao;
import com.assessment.fxtradingmultimicroservice.entity.Currencies;
import com.assessment.fxtradingmultimicroservice.entity.TradeInformation;
import com.assessment.fxtradingmultimicroservice.exception.NoDataAvailableException;
import com.assessment.fxtradingmultimicroservice.exception.ValidationException;

@Service
public class TradeService {
	
	private static final Logger logger = LoggerFactory.getLogger(FxtradingmultimicroserviceApplication.class);
	
	@Autowired
	TradeDao tradeDao;

	private Currencies currency;

	
	
	
	/**
	 * @author Aakanksha
	 * Checks for missing parameters in a TradeInformation object.
	 * 
	 * @param trade The TradeInformation object to check for missing parameters.
	 * @return A map of missing parameters, where the key is the parameter name, and the value is an error message.
	 * @throws ValidationException If one or more parameters are missing, this exception is thrown with a map of missing parameters.
	 */
  
	private Map<String, String> CheckMissing(TradeInformation trade) {
	    
	    Map<String, String> missingParameters = new LinkedHashMap<>();
	    
	    
	    if (trade.getTraderName() == null) {
	    	
	        missingParameters.put("TraderName", "Missing Trader Name");
	    }

	    if (trade.getCurrencyPair() == null) {
	    	
	        missingParameters.put("CurrencyPair", "Missing Currency pair");
	    }

	    if (trade.getOriginalAmount() == null) {
	    	
	        missingParameters.put("OriginalAmount", "Missing amount");
	    }
	    
	    if (!missingParameters.isEmpty()) {
	    	
	        throw new ValidationException(missingParameters);
	    }

	    return missingParameters;

	}

     
	/**
	 * @author Aakanksha
	 * Validates trader information including name, currency pair, and original amount.
	 *
	 * @param traderName The name of the trader.
	 * @param currencyPair The currency pair to validate.
	 * @param originalAmount The original amount to validate.
	 * @return A map of validation errors, where the key is the error field name, and the value is an error message.
	 * @throws ValidationException If validation errors are found, this exception is thrown with a map of validation errors.
	 */
    
	private Map<String, String> isValid(String traderName, String currencyPair, String originalAmount) {
	    
		Map<String, String>  validationErrors = new LinkedHashMap<>();

	    if (traderName.isBlank()) {
	    	
	    	validationErrors.put("TraderName","Trader name can't be empty!");
	    }

	   
	    if (currencyPair.isBlank()) {
	    	
	    	validationErrors.put("Currency pair","Currency pair can't be empty");
	    
	    }else {
	        
	    	currency = tradeDao.isPresent(currencyPair.toUpperCase());
	        
	    	if (currency == null) {
	        	
	    		validationErrors.put("Currency pair","Invalid Currency Pair");
	        }
	    }

	    
	    if ( !originalAmount.isBlank()) {
	       
	    	  try {
	        
	    		Double amount = Double.parseDouble(originalAmount);
	    
	    		if (amount < 0.0)
	    
	    			validationErrors.put("Amount","Amount must be Positive");
	      
	    	  }catch (NumberFormatException ne) {
	       
	        	   validationErrors.put("Amount","Amount must be a valid number");
	        }
	   }else {
	    	
		     validationErrors.put("Amount","Amount can't be empty!");
	   }

	    
	   if (!validationErrors.isEmpty()) {
	   
		   throw new ValidationException(validationErrors);
	   }

	    return validationErrors;
	}



	/**
	 * @author Aakanksha
	 * Books a trade based on the provided TradeInformation object.
	 *
	 * @param trade The TradeInformation object to book the trade.
	 * @return ResponseEntity containing the booking status and trade information or error messages.
	 */
	
	 public ResponseEntity<Map<String, String>> bookTrade(TradeInformation trade) {
    
	    Map<String,String> response = new LinkedHashMap<>();

        try {
       
    	    // Check for missing parameters
              
        	    response = CheckMissing(trade);
    	
            //Validate trader name, currency pair, and original amount
        
        	    response = isValid(trade.getTraderName(), trade.getCurrencyPair(), trade.getOriginalAmount());
    	
    
        }catch (ValidationException ve) {
        
		        response.put("Message", "Trade not booked");
		        response.putAll(ve.getErrorMessages());
		        return ResponseEntity.badRequest().body(response);
		 }  
  
        // No missing parameters or validation errors, proceed with booking
        
        Double amount = Double.parseDouble(trade.getOriginalAmount());
        Double finalAmount = amount * (Double.parseDouble(currency.getExchangeRate()));
        
        String transferredAmount = new DecimalFormat("###,###.##").format(finalAmount);
        String originalAmount = new DecimalFormat("###,###.##").format(amount);
        
        trade.setTransferredAmount(transferredAmount);
        trade.setOriginalAmount(originalAmount);
        trade.setCurrencyPair(trade.getCurrencyPair().toUpperCase());
        trade.setExchangeRateValue(currency.getExchangeRate());
        trade.setTradeDate(new java.sql.Timestamp(System.currentTimeMillis()));
        
        tradeDao.bookTrade(trade);
        
        response.put("Message","Trade for " + trade.getCurrencyPair() + " has been booked with rate " + trade.getExchangeRateValue() + ". The amount of " +currency.getCurrencySymbol()+" "+ trade.getTransferredAmount() 
        + " will be transferred in 2 working days to " + trade.getTraderName());
    
        return ResponseEntity.ok().body(response);
   
   }


	/**
	 * @author Aakanksha
	 * Retrieves a list of trade information from the tradeDao.
	 *
	 * @return ResponseEntity containing the list of trade information or a bad request with a message if no trades are available.
	 */
	public ResponseEntity<Object> getTrades() {
		
	    List<TradeInformation> trades = tradeDao.getTrades();
	    
	    try {
	    	
	    	logger.info("TradeInformation list empty : "+trades.isEmpty());
	    	
	    	if(trades.isEmpty()) {
	    	
	    		throw new NoDataAvailableException("No Trade Information Available....!");
	    	}
	    }catch(NoDataAvailableException ne) {
	    	
		    return ResponseEntity.badRequest().body(ne.getMessage());
	    }
	    
	    return ResponseEntity.ok().body(trades);
	    		
	    
	}


	      
		   
		
		
		
		
			
		
		    
		
	}
	
	

	


