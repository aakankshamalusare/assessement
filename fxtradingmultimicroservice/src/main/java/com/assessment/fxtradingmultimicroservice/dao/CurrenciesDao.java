package com.assessment.fxtradingmultimicroservice.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.assessment.fxtradingmultimicroservice.entity.Currencies;
import com.assessment.fxtradingmultimicroservice.entity.TradeInformation;

@Repository
public class CurrenciesDao {
	
	@Autowired
	SessionFactory factory;
	
	
   public Currencies isPresent(String currencyPair) {
		
		Session session = factory.openSession();
		
		Criteria criteria = session.createCriteria(Currencies.class);
		
		List<Currencies> exchangeRates = criteria.add(Restrictions.eq("currencyPair",currencyPair)).list();
		
		session.close();
		return (exchangeRates.isEmpty()? null:exchangeRates.get(0));	
		
	}

    public List<TradeInformation> getAllCurrency() {
		
		Session session = factory.openSession();
		Criteria criteria = session.createCriteria(Currencies.class);
		
		return criteria.list();
	}

    public ResponseEntity<Map<String, String>> addCurrencyPair(Currencies currencyPair) {
		
        Session session = factory.openSession();
		
		Transaction transaction = session.beginTransaction();
		
		session.save(currencyPair);
		
		transaction.commit();
		
		session.close();
		
		return null;
		
	}

	public void updateCurrencyPair(Currencies currencyPair) {
		
         Session session = factory.openSession();
         
         Transaction transaction = session.beginTransaction();
		
		 session.update(currencyPair);
		
		transaction.commit();
		session.close();
		
		
		
		
	}

	public boolean deleteCurrencyPair(String currencyPair) {
		
		Session session = factory.openSession();
        
        Transaction transaction = session.beginTransaction();
        
        Criteria criteria = session.createCriteria(Currencies.class);
        
        List<Currencies> currency =  criteria.add(Restrictions.eq("currencyPair",currencyPair.toUpperCase())).list();
        
        if(currency.isEmpty()) {
        	
        	return false;
        }
        
        session.delete(currency.get(0));
		transaction.commit();
		
		session.close();
		return true;
	}


}
