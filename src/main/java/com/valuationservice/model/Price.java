//Jesus
package com.valuationservice.model;

import java.math.BigDecimal;

import lombok.Data;

/**
 * Class Represents a price for asset.
 * 
 * @author Haridath Bodapati
 */

@Data
public class Price {
	private String assetId;
	private BigDecimal price;
	private String currency;

	public Price(String assetId, BigDecimal price, String currency) {
		this.assetId = assetId;
		this.price = price;
		this.currency = currency;
	}

	public Price(String assetId, BigDecimal price) {
		this.assetId = assetId;
		this.price = price;
	}
}
