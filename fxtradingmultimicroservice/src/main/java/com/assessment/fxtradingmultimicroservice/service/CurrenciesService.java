package com.assessment.fxtradingmultimicroservice.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.assessment.fxtradingmultimicroservice.dao.CurrenciesDao;
import com.assessment.fxtradingmultimicroservice.entity.Currencies;
import com.assessment.fxtradingmultimicroservice.entity.TradeInformation;
import com.assessment.fxtradingmultimicroservice.exception.NoDataAvailableException;
import com.assessment.fxtradingmultimicroservice.exception.ValidationException;

@Service
public class CurrenciesService {
	
	
	@Autowired
	CurrenciesDao currenciesDao;
	
	private Currencies currency;
	
	
	

	/**
	 * @author Aakanksha
	 * Checks for missing parameters in a Currencies object.
	 * 
	 * @param trade The  Currencies object to check for missing parameters.
	 * @return A map of missing parameters, where the key is the parameter name, and the value is an error message.
	 * @throws ValidationException If one or more parameters are missing, this exception is thrown with a map of missing parameters.
	 */
	
   private Map<String, String> CheckMissing(Currencies currency) {
	    
	    Map<String, String> missingParameters = new LinkedHashMap<>();

	    if (currency.getCurrencyPair() == null) {
	        missingParameters.put("Currency-Pair", "Missing Currency-Pair");
	    }

	    if (currency.getExchangeRate() == null) {
	        missingParameters.put("Exchange-Rate", "Missing Exchange-Rate");
	    }

	    if (!missingParameters.isEmpty()) {
	        throw new ValidationException(missingParameters);
	    }

	    return missingParameters;

	}
   
   private Map<String, String> isValid(String currencyPair,String currencySymbol, String exchangeRate ) {
	    
		Map<String, String>  validationErrors = new LinkedHashMap<>();
		
		
       if (currencyPair.isBlank()){
	    	
	    	validationErrors.put("Currency pair","Currency pair can't be empty");
	    
       }else {
	        
	    	currency = currenciesDao.isPresent(currencyPair.toUpperCase());
	        
	    	if (currency != null) {
	        	
	    		validationErrors.put("Currency pair Value","Already Currency Pair ");
	        }
	    }
       
       if (!exchangeRate.isBlank()) {
	       
	    	try {
	        
	    		Double amount = Double.parseDouble(exchangeRate);
	    
	    		if (amount < 0.0) {
	    
	    			validationErrors.put("ExchangeRate","ExchangeRate must be Positive");
	            }
	        
	    	}catch (NumberFormatException ne) {
	       
	        	   validationErrors.put("ExchangeRate","ExchangeRate must be a valid number");
	        }
	   } else {
	    	
		   validationErrors.put("ExchangeRate","ExchangeRate can't be empty!");
	   }
	    
	    
	   if (!validationErrors.isEmpty()) {
	   
		   throw new ValidationException(validationErrors);
	   }

	    return validationErrors;
	}

   public ResponseEntity<Map<String, String>> addCurrencyPair(Currencies currencyPair) {
		
		Map<String,String> response = new LinkedHashMap<>();
       
		try {
	       
	    	// Check for missing parameters
	        response = CheckMissing(currencyPair);
	        
	     // Validate 
	        response = isValid(currencyPair.getCurrencyPair(),currencyPair.getCurrencySymbol(),currencyPair.getExchangeRate());
	    	
	     }catch (ValidationException ve) {
	    	
	          if((ve.getErrorMessages().containsKey("Currency pair Value")&&!(ve.getErrorMessages().containsKey("ExchangeRate")))){
	    		
			            	System.out.println(ve.getErrorMessages().containsValue("Currency Pair Value"));
				    		
				    		currency.setExchangeRate(currencyPair.getExchangeRate());
				    		currenciesDao.updateCurrencyPair(currency);
				    		response.put("Message", "Currency-Pair updated successfully");
		    	
		    		        return ResponseEntity.ok().body(response);
		      }
	          

		    	response.put("Message","Currency-Pair not added");	
		    	response.putAll(ve.getErrorMessages());
		    	return ResponseEntity.badRequest().body(response);
		    	
	        
	     }  
	  
	    // No missing parameters or validation errors, proceed with booking
	       currenciesDao.addCurrencyPair(currencyPair);
           response.put("Message"," Currency Pair for " +currencyPair.getCurrencyPair() + " has been added with exchangeRate " + currencyPair.getExchangeRate());
       
           return ResponseEntity.ok().body(response);
		 
	}


   public ResponseEntity<Object> getAllCurrency() {
		
		 List<TradeInformation> currencies = currenciesDao.getAllCurrency();
		    
		 try {
		    	
			    if(currencies.isEmpty())
		    	
		    		throw new NoDataAvailableException("No Currency Information Available....!");
		    
		}catch(NoDataAvailableException ne) {
		    	
			    return ResponseEntity.badRequest().body(ne.getMessage());
		}
		    
		    return ResponseEntity.ok().body(currencies);
	 }
	
	
  private Map<String, String> isValid(String currencyPair, String exchangeRate) {
	 		
		  
	  		Map<String, String>  validationErrors = new LinkedHashMap<>();
	  		
	  		
	         if (currencyPair.isBlank()){
	 	    	
	 	    	validationErrors.put("Currency pair","Currency pair can't be empty");
	 	    
	         }else {
	 	        
	 	    	currency = currenciesDao.isPresent(currencyPair.toUpperCase());
	 	        
	 	    	if (currency == null) {
	 	        	
	 	    		validationErrors.put("Currency pair Value","Currency Pair not exists..");
	 	        }
	 	    }
	         
	         if (!exchangeRate.isBlank()) {
	  	       
	  	    	try {
	  	        
	  	    		Double amount = Double.parseDouble(exchangeRate);
	  	    
	  	    		if (amount < 0.0) {
	  	    
	  	    			validationErrors.put("ExchangeRate","ExchangeRate must be Positive");
	  	            }
	  	        
	  	    	}catch (NumberFormatException ne) {
	  	       
	  	        	   validationErrors.put("ExchangeRate","ExchangeRate must be a valid number");
	  	        }
	  	   } else {
	  	    	
	  		   validationErrors.put("ExchangeRate","ExchangeRate can't be empty!");
	  	   }
	  	    
	  	    
	  	   if (!validationErrors.isEmpty()) {
	  	   
	  		   throw new ValidationException(validationErrors);
	  	   }

	  	    return validationErrors;
	  	}

  public ResponseEntity<Map<String, String>> updateCurrencyPair(Currencies currencyPair) {
		
		
		Map<String,String> response = new LinkedHashMap<>();
	       
		try {
	       
	    	// Check for missing parameters
	        response = CheckMissing(currencyPair);
	        
	     // Validate 
	        response = isValid(currencyPair.getCurrencyPair(),currencyPair.getExchangeRate());
	    	
	     }catch (ValidationException ve) {
	    	
	    	 response.put("Message","Currency-Pair not updated...");	
		     response.putAll(ve.getErrorMessages());
		    return ResponseEntity.badRequest().body(response);
	          
	     }
		
		// No missing parameters or validation errors, proceed with updating
		currency.setExchangeRate(currencyPair.getExchangeRate());
		currenciesDao.updateCurrencyPair(currency);
		response.put("Message"," Currency Pair " +currencyPair.getCurrencyPair() + " has been updated with exchangeRate " + currencyPair.getExchangeRate());

        return ResponseEntity.ok().body(response);
	  
	    
		
		
	}

	
  private Map<String, String> isValid(String currencyPair){
   	
    Map<String, String>  validationErrors = new LinkedHashMap<>();
		
		if(currencyPair== null)
			
			validationErrors.put("Currency-Pair", "Missing Currency-Pair");
		
		if (currencyPair.isBlank())
		    	
		    	validationErrors.put("Currency pair","Currency pair can't be empty");
		    	
		    	
		if(!validationErrors.isEmpty()) {
			
			throw new ValidationException(validationErrors);
			
		}
		
		return validationErrors;
		
   }
			

  public ResponseEntity<Map<String, String>> deleteCurrencyPair(Currencies currencyPair) {
		
	      Map<String, String>  response = new LinkedHashMap<>();
		  
		  try {
			  
			  response = isValid(currencyPair.getCurrencyPair());
		 
		  }catch(ValidationException ve) {
			  
			  response.putAll(ve.getErrorMessages());
		      return ResponseEntity.badRequest().body(response);
			  
	      }
		  
		  
		 try {
			 
			 if(currenciesDao.deleteCurrencyPair(currencyPair.getCurrencyPair())) {
				 
				  response.put("Message", "Currency-Pair deleted successfully");
			      return ResponseEntity.ok().body(response);
			  }
			 throw new NoDataAvailableException("Currency-Pair not found");
			 
		 }catch(NoDataAvailableException na) {
			 
			 response.put("Message", na.getMessage());
			 
			 return ResponseEntity.badRequest().body(response);
	      }
		  
	      
	}


}
		  
		  
		


