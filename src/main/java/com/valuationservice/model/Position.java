//Jesus
package com.valuationservice.model;

import java.math.BigDecimal;

import lombok.Data;

/**
 * Class Represents a single position in an account with an asset and its
 * quantity.
 * 
 * @author Haridath Bodapati
 */
@Data
public class Position {
	private String assetId;
	private BigDecimal quantity;

	public Position(String assetId, BigDecimal quantity) {
		this.assetId = assetId;
		this.quantity = quantity;
	}

	public Position() {
	}
}
