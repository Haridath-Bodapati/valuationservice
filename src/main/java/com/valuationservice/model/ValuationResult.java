//Jesus
package com.valuationservice.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class Represents the result of a valuation for an account.
 * 
 * @author Haridath Bodapati
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValuationResult {
	private String accountId;
	private BigDecimal collateralValue;
	private BigDecimal marketValue;
	private Position position;
	private BigDecimal valuation;

	public ValuationResult(Position position, BigDecimal valuation) {
		this.position = position;
		this.valuation = valuation;
	}
}
