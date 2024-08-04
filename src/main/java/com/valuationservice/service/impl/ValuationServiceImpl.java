//Jesus
package com.valuationservice.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valuationservice.model.Eligibility;
import com.valuationservice.model.FXRate;
import com.valuationservice.model.Position;
import com.valuationservice.model.Price;
import com.valuationservice.model.ValuationResult;
import com.valuationservice.service.IEligibilityService;
import com.valuationservice.service.IFXService;
import com.valuationservice.service.IPositionsService;
import com.valuationservice.service.IPriceService;
import com.valuationservice.service.IValuationService;

import reactor.core.publisher.Mono;

/**
 * Class calculates collateral value & market value for given accounts, which hold certains positions.
 * Position = units (quantity) of certain assets, identified by assetId
 * 
 * Collateral value for account = Sum of collateral value for all positions in a given account.
 * Collateral value for ‘eligible’ position = quantity x price x discount factor.
 * Collateral value for ‘ineligible’ position = Zero.
 * Market value for both ‘eligible’ & ‘ineligible’ positions = quantity x price.
 * 
 * @author Haridath Bodapati
 */
@Service
public class ValuationServiceImpl implements IValuationService {

	@Autowired
	private IPositionsService positionsService;

	@Autowired
	private IEligibilityService eligibilityService;

	@Autowired
	private IPriceService priceService;

	@Autowired
	private IFXService fxService;

	private Set<String> extractAssetIds(List<Position> positions) {
		return positions.stream().map(Position::getAssetId).collect(Collectors.toSet());
	}

	/**
	 * 
	 * Method to prepare Valuation Results
	 * 
	 * @param positions
	 * @param eligibilityList
	 * @param prices
	 * @param fxRates
	 * @param currencyCode
	 * @return
	 */
	private List<ValuationResult> calculateValuationResults(List<Position> positions, List<Eligibility> eligibilityList,
			List<Price> prices, List<FXRate> fxRates, String currencyCode) {

		if (Objects.nonNull(positions) && Objects.nonNull(eligibilityList) && Objects.nonNull(prices)
				&& Objects.nonNull(fxRates) && Objects.nonNull(currencyCode) && positions.size() > 0
				&& eligibilityList.size() > 0 && prices.size() > 0 && fxRates.size() > 0
				&& Objects.nonNull(currencyCode) && !currencyCode.isBlank()) {

			Map<String, Price> priceMap = prices.stream().collect(Collectors.toMap(Price::getAssetId, price -> price));

			Map<String, Eligibility> eligibilityMap = eligibilityList.stream()
					.flatMap(e -> e.getAccountIDs().stream()
							.flatMap(accountId -> e.getAssetIDs().stream()
									.map(assetId -> Map.entry(accountId, new AbstractMap.SimpleEntry<>(assetId, e)))))
					.collect(Collectors.toMap(entry -> entry.getKey(), // accountId is the key
							entry -> entry.getValue().getValue(), // Eligibility is the value
							(e1, e2) -> e1 // Merge function, in case of duplicate keys
					));

			Map<String, BigDecimal> fxMap = fxRates.stream()
					.collect(Collectors.toMap(FXRate::getCurrency, FXRate::getMultiplier));

			BigDecimal currencyMultiplier = fxMap.getOrDefault(currencyCode, BigDecimal.ONE);

			Map<String, ValuationResult> resultMap = new HashMap<>();

			for (Position position : positions) {
				String assetId = position.getAssetId();
				BigDecimal quantity = position.getQuantity();
				Price price = priceMap.get(assetId);

				if (price != null) {
					BigDecimal assetPrice = price.getPrice();
		            BigDecimal convertedPrice = assetPrice.multiply(currencyMultiplier);
		            BigDecimal marketValue = quantity.multiply(convertedPrice).setScale(2, RoundingMode.HALF_UP);
		            
		            String accountId = position.getAccountId();
		            Eligibility eligibility = eligibilityMap.getOrDefault(new AbstractMap.SimpleEntry<>(accountId, assetId), null);

		            BigDecimal collateralValue = BigDecimal.ZERO;
		            
		            if (eligibility != null && eligibility.isEligible()) {
		                BigDecimal discountFactor = eligibility.getDiscount();
		                collateralValue = marketValue.multiply(discountFactor).setScale(2, RoundingMode.HALF_UP);
		            }
		            
		            ValuationResult result = resultMap.getOrDefault(accountId, new ValuationResult(accountId, BigDecimal.ZERO, BigDecimal.ZERO));
		            
		            result.setCollateralValue(result.getCollateralValue().add(collateralValue).setScale(2, RoundingMode.HALF_UP));
		            result.setMarketValue(result.getMarketValue().add(marketValue).setScale(2, RoundingMode.HALF_UP));

					resultMap.put(assetId, result);
				}
			}

			return new ArrayList<>(resultMap.values());
		}
		return null;
	}

	/**
	 * @see IValuationService
	 */

	@Override
	public Mono<List<ValuationResult>> calculateValuation(List<String> accountIds, String currencyCode) {
		if (Objects.nonNull(currencyCode) && Objects.nonNull(accountIds) && !currencyCode.isBlank()) {
			return positionsService.getPositions(accountIds).flatMap(positions -> {
				Set<String> assetIds = extractAssetIds(positions);

				return eligibilityService.getEligibility(accountIds, new ArrayList<>(assetIds))
						.flatMap(eligibilityList -> {
							return priceService.getPrices(new ArrayList<>(assetIds)).flatMap(prices -> {
								return fxService.getFxRates().map(fxRates -> {
									List<Price> priceList = convertToPriceList(prices);
									List<FXRate> fxRateList = convertToFXRateList(fxRates);

									List<ValuationResult> valuationResults = calculateValuationResults(positions,
											eligibilityList, priceList, fxRateList, currencyCode);
									return valuationResults;
								});
							});
						});
			});
		}
		return null;
	}

	/**
	 * Method to convert list of asset prices to list of Price.
	 * 
	 * @param assetPrices
	 * @return
	 */
	private List<Price> convertToPriceList(List<Price> prices) {
		if (Objects.nonNull(prices) && prices.size() > 0) {
			return prices.stream().map(assetPrice -> new Price(assetPrice.getAssetId(), assetPrice.getPrice()))
					.collect(Collectors.toList());
		}
		return null;
	}

	/**
	 * Method to convert map of fxMap to list of FXRate.
	 * 
	 * @param fxMap
	 * @return
	 */
	private List<FXRate> convertToFXRateList(Map<String, BigDecimal> fxMap) {
		if (Objects.nonNull(fxMap) && fxMap.size() > 0) {
			return fxMap.entrySet().stream().map(entry -> new FXRate(entry.getKey(), entry.getValue()))
					.collect(Collectors.toList());
		}
		return null;
	}

}
