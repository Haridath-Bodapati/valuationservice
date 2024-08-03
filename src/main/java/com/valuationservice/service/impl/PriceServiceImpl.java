//Jesus
package com.valuationservice.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.valuationservice.model.Price;
import com.valuationservice.service.IPriceService;

import reactor.core.publisher.Mono;

/**
 * Represents Service class for getting Prices.
 * 
 * @author Haridath Bodapati
 */
@Service
public class PriceServiceImpl implements IPriceService {

	/**
	 * @see IPriceService
	 */
	@Override
	public Mono<List<Price>> getPrices(List<String> assetIds) {
		// Provide mock data for prices
		return Mono.just(List.of(new Price("asset1", BigDecimal.valueOf(100), "USD"),
				new Price("asset2", BigDecimal.valueOf(200), "GBP")));
	}
}
