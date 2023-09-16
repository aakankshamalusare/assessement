package com.assessment.fxtradingmultimicroservice.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assessment.fxtradingmultimicroservice.entity.Currencies;
import com.assessment.fxtradingmultimicroservice.service.CurrenciesService;


@RestController

@RequestMapping(value="/currency")
public class CurrenciesController {
	
	
	
	@Autowired 
	CurrenciesService currenciesService;
	
	/**
	 * 
	 * @author Aakanksha
	 *  Retrieves all currency pairs.
	 *  
	 * @return ResponseEntity containing the list of currency pairs
	 */
	@GetMapping(value="get-all-currencyPair")
    public ResponseEntity<Object> getAllCurrency(){
    	
    	return currenciesService.getAllCurrency();
    }
    
	/**
	 * 
	 * @author Aakanksha
	 * Adds a new currency pair.
	 * 
	 * @param currencyPair The currency pair to add.
	 * @return ResponseEntity with a message indicating the success of the operation.
	 */
    @PostMapping(value="add-currencyPair")
    public ResponseEntity<Map<String,String>> addCurrencyPair(@RequestBody Currencies currencyPair){
    	
    	return currenciesService.addCurrencyPair(currencyPair);
    }
    
    
    /**
     * 
     * @author Aakanksha
     * Updates an existing currency pair.
     * 
     * @param currencyPair The currency pair to update.
     * @return ResponseEntity with a message indicating the success of the operation.
     */
    @PutMapping(value="update-currencyPair")
    public ResponseEntity<Map<String,String>> updateCurrencyPair(@RequestBody Currencies currencyPair){
    	
    	return currenciesService.updateCurrencyPair(currencyPair);
    }

    
    /**
     * 
     * @author Aakanksha
     * Deletes a currency pair.
     * 
     * @param currencyPair The currency pair to delete.
     * @return ResponseEntity with a message indicating the success of the operation.
     */
    @DeleteMapping(value="delete-currencyPair")
    public ResponseEntity<Map<String,String>> deleteCurrencyPair(@RequestBody Currencies currencyPair){
    	
    	return currenciesService.deleteCurrencyPair(currencyPair);
    }
    

}
