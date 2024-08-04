//Jesus
package com.valuationservice.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.valuationservice.model.Position;
import com.valuationservice.service.IPositionsService;

import reactor.core.publisher.Mono;

/**
 * Represents Service class for getting Positions.
 * 
 * @author Haridath Bodapati
 */
@Service
public class PostionsServiceImpl implements IPositionsService {

	/**
	 * @see IPositionsService
	 */
	@Override
	public Mono<List<Position>> getPositions(List<String> accountIds) {
		// Provide mock data for positions
		return Mono.empty();
	}
}
