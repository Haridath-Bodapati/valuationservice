//Jesus
package com.valuationservice.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class Represents a single position in an account with an asset and its
 * quantity.
 * 
 * @author Haridath Bodapati
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Position {
	private String assetId;
	private BigDecimal quantity;
	private String accountId;
}
