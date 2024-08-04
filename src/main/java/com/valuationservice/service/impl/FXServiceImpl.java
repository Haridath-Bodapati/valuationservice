//Jesus
package com.valuationservice.service.impl;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.valuationservice.service.IFXService;

import reactor.core.publisher.Mono;

/**
 * Represents Service class for getting FxRates.
 * 
 * @author Haridath Bodapati
 */
@Service
public class FXServiceImpl implements IFXService {

	/**
	 * @see IFXService
	 */

	@Override
	public Mono<Map<String, BigDecimal>> getFxRates() {
		// Provide mock data for FX rates
		return Mono.empty();
	}
}
