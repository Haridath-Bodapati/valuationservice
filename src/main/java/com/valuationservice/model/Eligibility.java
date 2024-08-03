//Jesus
package com.valuationservice.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class Represents the eligibility details for an asset in an account.
 * 
 * @author Haridath Bodapati
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Eligibility {
	public Eligibility(List<String> accountIDs, List<String> assestIDs) {
		this.accountIDs = accountIDs;
		this.assetIDs = assestIDs;
	}

	public Eligibility(String string, List<String> of) {
	}

	private boolean eligible;
	private List<String> assetIDs;
	private List<String> accountIDs;
	private BigDecimal discount;
}
