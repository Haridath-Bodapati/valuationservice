//Jesus
package com.valuationservice.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.valuationservice.model.Eligibility;
import com.valuationservice.service.IEligibilityService;

import reactor.core.publisher.Mono;

/**
 * Represents Service class for getting eligibilities.
 * 
 * @author Haridath Bodapati
 */

@Service
public class EligibilityServiceImpl implements IEligibilityService {

	/**
	 * @see IEligibilityService
	 */

	@Override
	public Mono<List<Eligibility>> getEligibility(List<String> accountIds, List<String> assetIds) {
		return Mono.just(List.of(new Eligibility(List.of("account1"), List.of("asset1")),
				new Eligibility(List.of("account2"), List.of("asset2"))));
	}
}