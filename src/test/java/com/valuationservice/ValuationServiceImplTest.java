//Jesus
package com.valuationservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.valuationservice.model.Eligibility;
import com.valuationservice.model.Position;
import com.valuationservice.model.Price;
import com.valuationservice.model.ValuationResult;
import com.valuationservice.service.IEligibilityService;
import com.valuationservice.service.IFXService;
import com.valuationservice.service.IPositionsService;
import com.valuationservice.service.IPriceService;
import com.valuationservice.service.impl.ValuationServiceImpl;

import reactor.core.publisher.Mono;

/**
 * Test class to test the ValutionServiceImpl functionality.
 * 
 * @author Haridath Bodapati
 */

public class ValuationServiceImplTest {

	@Mock
	private IPositionsService positionsService;

	@Mock
	private IEligibilityService eligibilityService;

	@Mock
	private IPriceService priceService;

	@Mock
	private IFXService fxService;

	@InjectMocks
	private ValuationServiceImpl valuationService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	/**
	 * Method to test the getPositions functionality
	 */

	@Test
	public void testGetPositions() {
		// Mock data for positions
		List<Position> positions = List.of(new Position("S1", BigDecimal.valueOf(100)),
				new Position("S3", BigDecimal.valueOf(100)), new Position("S4", BigDecimal.valueOf(100)));

		// Mocking PositionsService's getPositions method
		when(positionsService.getPositions(List.of("E1"))).thenReturn(Mono.just(positions));

		// Using the service in ValuationService and verifying the output
		Mono<List<Position>> result = positionsService.getPositions(List.of("E1"));
		List<Position> resultPositions = result.block(); // Using block() for testing purposes

		// Asserting the expected output
		assertEquals(3, resultPositions.size());
		assertEquals("S1", resultPositions.get(0).getAssetId());
		assertEquals("S3", resultPositions.get(1).getAssetId());
		assertEquals("S4", resultPositions.get(2).getAssetId());
		assertEquals(BigDecimal.valueOf(100), resultPositions.get(0).getQuantity());
	}

	/**
	 * Method to test the getEligibility functionality
	 */
	@Test
	public void testGetEligibility() {
		// Mocking EligibilityService response
		when(eligibilityService.getEligibility(List.of("E1", "E2"), List.of("S1", "S2", "S3", "S4", "S5")))
				.thenReturn(Mono.just(List.of(
						new Eligibility(true, List.of("S1", "S2", "S3"), List.of("E1", "E2"), BigDecimal.valueOf(0.9)),
						new Eligibility(false, List.of("S4", "S5"), List.of("E1", "E2"), BigDecimal.valueOf(0.0)))));

		// Calling getEligibility
		Mono<List<Eligibility>> resultMono = eligibilityService.getEligibility(List.of("E1", "E2"),
				List.of("S1", "S2", "S3", "S4", "S5"));

		// Verifying results
		List<Eligibility> result = resultMono.block();
		assertNotNull(result);
		assertEquals(2, result.size());

		// Checking first eligibility entry
		Eligibility firstEligibility = result.get(0);
		assertEquals(true, firstEligibility.isEligible());
		assertEquals(List.of("S1", "S2", "S3"), firstEligibility.getAssetIDs());
		assertEquals(List.of("E1", "E2"), firstEligibility.getAccountIDs());
		assertEquals(BigDecimal.valueOf(0.9), firstEligibility.getDiscount());

		// Checking second eligibility entry
		Eligibility secondEligibility = result.get(1);
		assertEquals(false, secondEligibility.isEligible());
		assertEquals(List.of("S4", "S5"), secondEligibility.getAssetIDs());
		assertEquals(List.of("E1", "E2"), secondEligibility.getAccountIDs());
	}

	/**
	 * Method to test the getPrices functionality
	 */
	@Test
	public void testGetPrices() {
		// Mocking PriceService response
		when(priceService.getPrices(List.of("S1", "S2", "S3", "S4"))).thenReturn(Mono.just(List.of(
				new Price("S1", BigDecimal.valueOf(50.5), "GBP"), new Price("S2", BigDecimal.valueOf(20.2), "JPY"),
				new Price("S3", BigDecimal.valueOf(10.4), "GBP"), new Price("S4", BigDecimal.valueOf(15.5), "USD"))));

		// Calling getPrices
		Mono<List<Price>> resultMono = priceService.getPrices(List.of("S1", "S2", "S3", "S4"));

		// Verifying results
		List<Price> results = resultMono.block();
		assertNotNull(results);
		assertEquals(4, results.size());

		// Checking prices
		Price priceS1 = results.get(0);
		assertEquals("S1", priceS1.getAssetId());
		assertEquals(BigDecimal.valueOf(50.5), priceS1.getPrice());
		assertEquals("GBP", priceS1.getCurrency());

		Price priceS2 = results.get(1);
		assertEquals("S2", priceS2.getAssetId());
		assertEquals(BigDecimal.valueOf(20.2), priceS2.getPrice());
		assertEquals("JPY", priceS2.getCurrency());

		Price priceS3 = results.get(2);
		assertEquals("S3", priceS3.getAssetId());
		assertEquals(BigDecimal.valueOf(10.4), priceS3.getPrice());
		assertEquals("GBP", priceS3.getCurrency());

		Price priceS4 = results.get(3);
		assertEquals("S4", priceS4.getAssetId());
		assertEquals(BigDecimal.valueOf(15.5), priceS4.getPrice());
		assertEquals("USD", priceS4.getCurrency());
	}

	/**
	 * Method to test the getFxRates functionality
	 */

	@Test
	public void testGetFxRates() {
		// Mocking FXService response
		when(fxService.getFxRates())
				.thenReturn(Mono.just(Map.of("JPY", BigDecimal.valueOf(0.0062), "GBP", BigDecimal.valueOf(1.28))));

		// Calling getFxRates
		Mono<Map<String, BigDecimal>> resultMono = fxService.getFxRates();

		// Verifying results
		Map<String, BigDecimal> results = resultMono.block();
		assertEquals(2, results.size());

		// Checking FX rates
		assertEquals(BigDecimal.valueOf(0.0062), results.get("JPY"));
		assertEquals(BigDecimal.valueOf(1.28), results.get("GBP"));
	}

	/**
	 * Method to test the ValuationCalculations.
	 */
	@Test
	public void testCalculateValuation() {
		// Mocking positions
		when(positionsService.getPositions(List.of("account1"))).thenReturn(Mono.just(List
				.of(new Position("asset1", BigDecimal.valueOf(10)), new Position("asset2", BigDecimal.valueOf(20)))));

		// Mocking eligibility
		when(eligibilityService.getEligibility(List.of("account1"), List.of("asset1", "asset2")))
				.thenReturn(Mono.just(List.of(new Eligibility(List.of("account1"), List.of("asset1")),
						new Eligibility(List.of("account2"), List.of("asset2")))));

		// Mocking prices
		when(priceService.getPrices(List.of("asset1", "asset2")))
				.thenReturn(Mono.just(List.of(new Price("asset1", BigDecimal.valueOf(100), "USD"),
						new Price("asset2", BigDecimal.valueOf(200), "GBP"))));

		// Mocking FX rates
		when(fxService.getFxRates())
				.thenReturn(Mono.just(Map.of("USD", BigDecimal.valueOf(1), "EUR", BigDecimal.valueOf(0.85))));

		// Call the method under test
		Mono<List<ValuationResult>> resultMono = valuationService.calculateValuation(List.of("account1"), "USD");

		assertNotNull(resultMono);

		// Verify results
		List<ValuationResult> results = resultMono.block();
		assertEquals(2, results.size());
		assertEquals(new ValuationResult(new Position("asset1", BigDecimal.valueOf(10)), BigDecimal.valueOf(1000)),
				results.get(0));
		assertEquals(new ValuationResult(new Position("asset2", BigDecimal.valueOf(20)), BigDecimal.valueOf(4000)),
				results.get(1));
	}

	/**
	 * Method to test the ValuationCalculations with Empty Positions.
	 */

	@Test
	public void testCalculateValuation_EmptyPositions() {
		// Mocking empty positions
		when(positionsService.getPositions(List.of("account1"))).thenReturn(Mono.just(List.of()));

		// Mocking eligibility, prices, and FX rates
		when(eligibilityService.getEligibility(List.of("account1"), List.of())).thenReturn(Mono.just(List.of()));
		when(priceService.getPrices(List.of())).thenReturn(Mono.just(List.of()));
		// Mocking FX rates
		when(fxService.getFxRates())
				.thenReturn(Mono.just(Map.of("USD", BigDecimal.valueOf(1), "EUR", BigDecimal.valueOf(0.85))));

		// Call the method under test
		Mono<List<ValuationResult>> resultMono = valuationService.calculateValuation(List.of("account1"), "USD");

		assertNotNull(resultMono);
	}

	/**
	 * Method to test the ValuationCalculations with different Currencies.
	 */

	@Test
	public void testCalculateValuation_differentCurrency() {
		// Mocking positions
		when(positionsService.getPositions(List.of("account1"))).thenReturn(Mono.just(List
				.of(new Position("asset1", BigDecimal.valueOf(10)), new Position("asset2", BigDecimal.valueOf(20)))));

		// Mocking eligibility
		when(eligibilityService.getEligibility(List.of("account1"), List.of("asset1", "asset2")))
				.thenReturn(Mono.just(List.of(new Eligibility(List.of("account1"), List.of("asset1")),
						new Eligibility(List.of("account2"), List.of("asset2")))));

		// Mocking prices
		when(priceService.getPrices(List.of("asset1", "asset2")))
				.thenReturn(Mono.just(List.of(new Price("asset3", BigDecimal.valueOf(100), "USD"),
						new Price("asset1", BigDecimal.valueOf(100), "EUR"),
						new Price("asset2", BigDecimal.valueOf(200), "GBP"))));

		// Mocking FX rates
		when(fxService.getFxRates())
				.thenReturn(Mono.just(Map.of("USD", BigDecimal.valueOf(1), "EUR", BigDecimal.valueOf(0.85))));

		// Call the method under test
		Mono<List<ValuationResult>> resultMono = valuationService.calculateValuation(List.of("account1"), "EUR");

		assertNotNull(resultMono);

		// Verify results with converted currency
		List<ValuationResult> results = resultMono.block();
		assertEquals(2, results.size());

		// Define expected values with consistent scale
		BigDecimal expectedValuation1 = BigDecimal.valueOf(850).setScale(2);
		BigDecimal expectedValuation2 = BigDecimal.valueOf(3400.00).setScale(2);

		// Adjust the assertions to use delta for BigDecimal comparisons
		assertEquals(expectedValuation1, results.get(0).getValuation(),
				"Valuation for asset1 should match expected value");
		assertEquals(expectedValuation2, results.get(1).getValuation(),
				"Valuation for asset2 should match expected value");
	}

	/**
	 * Method to test the exception handling of CalculateValuation.
	 */

	@Test
	public void testCalculateValuation_Failure() {
		// Simulate an error in getting positions
		when(positionsService.getPositions(List.of("account1")))
				.thenReturn(Mono.error(new RuntimeException("Service unavailable")));

		// Call the method under test
		Mono<List<ValuationResult>> resultMono = valuationService.calculateValuation(List.of("account1"), "USD");

		// Verify handling of the error
		List<ValuationResult> results = resultMono.doOnError(e -> assertEquals("Service unavailable", e.getMessage()))
				.onErrorResume(e -> Mono.just(List.of())) // Provide fallback for the test
				.block();

		assertEquals(0, results.size());
	}

	/**
	 * Method to test the ValuationCalculations with null values for accountIds and
	 * currencyCode.
	 */
	@Test
	public void testCalculateValuation_null_values() {
		// Mocking positions
		when(positionsService.getPositions(List.of("account1"))).thenReturn(Mono.just(List
				.of(new Position("asset1", BigDecimal.valueOf(10)), new Position("asset2", BigDecimal.valueOf(20)))));

		// Mocking eligibility
		when(eligibilityService.getEligibility(List.of("account1"), List.of("asset1", "asset2")))
				.thenReturn(Mono.just(List.of(new Eligibility(List.of("account1"), List.of("asset1")),
						new Eligibility(List.of("account2"), List.of("asset2")))));

		// Mocking prices
		when(priceService.getPrices(List.of("asset1", "asset2")))
				.thenReturn(Mono.just(List.of(new Price("asset1", BigDecimal.valueOf(100), "USD"),
						new Price("asset2", BigDecimal.valueOf(200), "GBP"))));

		// Mocking FX rates
		when(fxService.getFxRates())
				.thenReturn(Mono.just(Map.of("USD", BigDecimal.valueOf(1), "EUR", BigDecimal.valueOf(0.85))));

		// Call the method under test
		Mono<List<ValuationResult>> resultMono = valuationService.calculateValuation(null, null);
		assertNull(resultMono);

	}

	/**
	 * Method to test the ValuationCalculations with null values for accountIds and
	 * currencyCode.
	 */
	@Test
	public void testCalculateValuation_empty_values() {
		// Mocking positions
		when(positionsService.getPositions(List.of("account1"))).thenReturn(Mono.just(List
				.of(new Position("asset1", BigDecimal.valueOf(10)), new Position("asset2", BigDecimal.valueOf(20)))));

		// Mocking eligibility
		when(eligibilityService.getEligibility(List.of("account1"), List.of("asset1", "asset2")))
				.thenReturn(Mono.just(List.of(new Eligibility(List.of("account1"), List.of("asset1")),
						new Eligibility(List.of("account2"), List.of("asset2")))));

		// Mocking prices
		when(priceService.getPrices(List.of("asset1", "asset2")))
				.thenReturn(Mono.just(List.of(new Price("asset1", BigDecimal.valueOf(100), "USD"),
						new Price("asset2", BigDecimal.valueOf(200), "GBP"))));

		// Mocking FX rates
		when(fxService.getFxRates())
				.thenReturn(Mono.just(Map.of("USD", BigDecimal.valueOf(1), "EUR", BigDecimal.valueOf(0.85))));

		// Call the method under test
		Mono<List<ValuationResult>> resultMono = valuationService.calculateValuation(new ArrayList(), "");
		assertNull(resultMono);

	}

}
