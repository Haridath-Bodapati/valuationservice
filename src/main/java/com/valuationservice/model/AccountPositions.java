//Jesus
package com.valuationservice.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class Represents the positions held in an account.
 * 
 * @author Haridath Bodapati
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountPositions {
	private String accountId;
	private List<Position> positions;

}
