package dev.tarasshablii.opora.microservices.apigateway.config;

import dev.tarasshablii.opora.microservices.apigateway.provider.rest.initiatives.api.InitiativesApi;
import dev.tarasshablii.opora.microservices.apigateway.provider.rest.media.api.MediaApi;
import dev.tarasshablii.opora.microservices.apigateway.provider.rest.sponsors.api.SponsorsApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

	@Value("${initiatives.service.url}")
	private String initiativesServiceUrl;

	@Value("${media.service.url}")
	private String mediaServiceUrl;

	@Value("${sponsors.service.url}")
	private String sponsorsServiceUrl;

	@Bean
	public WebClient mediaWebClient() {
		return WebClient.builder()
							 .baseUrl(mediaServiceUrl.concat("/v1/media"))
							 .codecs(configurer -> configurer.defaultCodecs()
																		.maxInMemorySize(10 * 1024 * 1024)) // 10 MB
							 .build();
	}

	@Bean
	public dev.tarasshablii.opora.microservices.apigateway.provider.rest.initiatives.ApiClient initiativesApiClient() {
		var client = new dev.tarasshablii.opora.microservices.apigateway.provider.rest.initiatives.ApiClient();
		client.setBasePath(initiativesServiceUrl);
		return client;
	}

	@Bean
	public dev.tarasshablii.opora.microservices.apigateway.provider.rest.media.ApiClient mediaApiClient() {
		var client = new dev.tarasshablii.opora.microservices.apigateway.provider.rest.media.ApiClient();
		client.setBasePath(mediaServiceUrl);
		return client;
	}

	@Bean
	public dev.tarasshablii.opora.microservices.apigateway.provider.rest.sponsors.ApiClient sponsorsApiClient() {
		var client = new dev.tarasshablii.opora.microservices.apigateway.provider.rest.sponsors.ApiClient();
		client.setBasePath(sponsorsServiceUrl);
		return client;
	}

	@Bean
	public InitiativesApi initiativesApi() {
		return new InitiativesApi(initiativesApiClient());
	}

	@Bean
	public MediaApi mediaApi() {
		return new MediaApi(mediaApiClient());
	}

	@Bean
	public SponsorsApi sponsorsApi() {
		return new SponsorsApi(sponsorsApiClient());
	}

}
