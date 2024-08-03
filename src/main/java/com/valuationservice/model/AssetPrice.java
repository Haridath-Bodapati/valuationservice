//Jesus
package com.valuationservice.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class Represents the Represents the price of an asset.
 * 
 * @author Haridath Bodapati
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetPrice {
	private String assetId;
	private BigDecimal price;
	private String currency;
}
