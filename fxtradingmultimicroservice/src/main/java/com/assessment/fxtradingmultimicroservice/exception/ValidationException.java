package com.assessment.fxtradingmultimicroservice.exception;

import java.util.LinkedHashMap;
import java.util.Map;

public class ValidationException extends RuntimeException {
    
	private Map<String, String> validationErrors = new LinkedHashMap<>();

    public ValidationException(Map<String, String> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public Map<String, String> getErrorMessages() {
        
    	return validationErrors;
    }
}
