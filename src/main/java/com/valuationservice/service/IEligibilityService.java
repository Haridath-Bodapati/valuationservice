//Jesus
package com.valuationservice.service;

import java.util.List;

import com.valuationservice.model.Eligibility;

import reactor.core.publisher.Mono;

/**
 * Represents EligibilityService interface for getting eligibility.
 * 
 * @author Haridath Bodapati
 */
public interface IEligibilityService {

	/**
	 * Method to get the Eligibility for accounts and assets.
	 * 
	 * @param accountIds
	 * @param assetIds
	 * @return
	 */

	Mono<List<Eligibility>> getEligibility(List<String> accountIds, List<String> assetIds);
}
