//Jesus
package com.valuationservice.service;

import java.util.List;

import com.valuationservice.model.Position;

import reactor.core.publisher.Mono;

/**
 * Represents PositionsService interface for getting rates.
 * 
 * @author Haridath Bodapati
 */

public interface IPositionsService {

	/**
	 * Method to get the Positions for accounts.
	 * 
	 * @param accountIds
	 * @return
	 */
	Mono<List<Position>> getPositions(List<String> accountIds);
}