//Jesus
package com.valuationservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class for creating a WebClient bean.
 * 
 * @author Haridath Bodapati
 */
@Configuration
public class WebClientConfig {

	@Value("${services.positions.url}")
	private String positionsServiceUrl;

	@Value("${services.eligibility.url}")
	private String eligibilityServiceUrl;

	@Value("${services.prices.url}")
	private String priceServiceUrl;

	@Value("${services.fxrates.url}")
	private String fxServiceUrl;

	/**
	 * Method to create WebCLient.builder
	 * 
	 * @return WebCLient.builder
	 */

	@Bean
	public WebClient.Builder webClientBuilder() {
		return WebClient.builder();
	}

	/**
	 * Method to create WebCLient
	 * 
	 * @param builder
	 * @return WebCLient
	 */

	@Bean
	public WebClient positionsWebClient(WebClient.Builder builder) {
		return builder.baseUrl(positionsServiceUrl).build();
	}

	/**
	 * Method to create WebCLient
	 * 
	 * @param builder
	 * @return WebCLient
	 */
	@Bean
	public WebClient eligibilityWebClient(WebClient.Builder builder) {
		return builder.baseUrl(eligibilityServiceUrl).build();
	}

	/**
	 * Method to create WebCLient
	 * 
	 * @param builder
	 * @return WebCLient
	 */
	@Bean
	public WebClient priceWebClient(WebClient.Builder builder) {
		return builder.baseUrl(priceServiceUrl).build();
	}

	/**
	 * Method to create WebCLient
	 * 
	 * @param builder
	 * @return
	 */
	@Bean
	public WebClient fxWebClient(WebClient.Builder builder) {
		return builder.baseUrl(fxServiceUrl).build();
	}

}
