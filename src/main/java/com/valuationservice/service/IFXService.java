//Jesus
package com.valuationservice.service;

import java.math.BigDecimal;
import java.util.Map;

import reactor.core.publisher.Mono;

/**
 * Represents FXService interface for getting rates.
 * 
 * @author Haridath Bodapati
 */
public interface IFXService {

	/**
	 * Method to get the Fx Rates.
	 * 
	 * @return
	 */
	Mono<Map<String, BigDecimal>> getFxRates();
}
