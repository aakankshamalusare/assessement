package com.example.fxtrading.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.fxtrading.dto.Trade;
import com.example.fxtrading.service.TradeService;

@RestController

@RequestMapping(value = "/trade")
public class TradeController {

    @Autowired
    private TradeService tradeService;


    /**
     * 
     * Handles a POST request to book a trade using the provided Trade object.
     * @param trade The Trade object to be booked.
     * @return ResponseEntity containing booking status or validation errors.
     * 
     */

    @PostMapping(value="/book-trade")
    public ResponseEntity<Map<String, String>> bookTrade(@RequestBody Trade trade){

     
        return tradeService.bookTrade(trade);
        
    }

    /**
     * Handles a GET request to retrieve trade information.
     * @return ResponseEntity containing trade information or an error message
     */

    @GetMapping(value="get-trades")
    public ResponseEntity<Object> getTrades(){
        
        return tradeService.getTrades();

    }



    
    
}
