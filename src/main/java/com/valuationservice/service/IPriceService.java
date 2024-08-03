//Jesus
package com.valuationservice.service;

import java.util.List;

import com.valuationservice.model.Price;

import reactor.core.publisher.Mono;

/**
 * Represents PriceService interface for getting prices.
 * 
 * @author Haridath Bodapati
 */
public interface IPriceService {

	/**
	 * Method to get the Prices for assets.
	 * 
	 * @param assetIds
	 * @return
	 */
	Mono<List<Price>> getPrices(List<String> assetIds);
}