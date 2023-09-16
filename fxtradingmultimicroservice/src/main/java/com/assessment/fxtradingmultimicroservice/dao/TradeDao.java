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
public class TradeDao {
	
	@Autowired
	SessionFactory factory;

	public Currencies isPresent(String currencyPair) {
		
		Session session = factory.openSession();
	    
		Criteria criteria = session.createCriteria(Currencies.class);
		
		List<Currencies> exchangeRates = criteria.add(Restrictions.eq("currencyPair",currencyPair)).list();
		
		session.close();
		
		return (exchangeRates.isEmpty()? null:exchangeRates.get(0));
	}

	
	public boolean bookTrade(TradeInformation tradeInformation) {
		
		Session session = factory.openSession();
		
		Transaction transaction = session.beginTransaction();
		
	    session.save(tradeInformation);
		
		transaction.commit();
		
		session.close();
		
		return true;
	}

	
	public List<TradeInformation> getTrades() {
		
		
		Session session = factory.openSession();
		
		Criteria criteria = session.createCriteria(TradeInformation.class);
		
		return criteria.list();
	}

	
}
