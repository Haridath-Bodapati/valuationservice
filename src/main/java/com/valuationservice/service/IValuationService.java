//Jesus
package com.valuationservice.service;

import java.util.List;

import com.valuationservice.model.ValuationResult;

import reactor.core.publisher.Mono;

/**
 * Represents Service interface for calculating valuations.
 * 
 * @author Haridath Bodapati
 */

public interface IValuationService {

	/**
	 * Calculates the valuation for given accounts.
	 *
	 * @param accountIds   List of account IDs to calculate valuation for.
	 * @param currencyCode Currency code to convert the valuation to.
	 * @return A Mono containing a list of ValuationResult.
	 */
	Mono<List<ValuationResult>> calculateValuation(List<String> accountIds, String currencyCode);
}