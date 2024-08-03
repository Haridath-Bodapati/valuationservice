//Jesus
package com.valuationservice.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class Represents the foreign exchange rate for a currency.
 * 
 * @author Haridath Bodapati
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FXRate {
	private String currency;
	private BigDecimal multiplier;
}
