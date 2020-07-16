package com.kpk.cinematickets.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository

@Configuration
class Oauth2Config(val env: Environment) {
    companion object {
        private const val CLIENT = "google"
        private const val CLIENT_PROPERTY_KEY = "spring.security.oauth2.client.registration"
    }

    @Bean
    fun clientRegistrationRepository(): ClientRegistrationRepository {
        val clientId: String = env.getProperty("$CLIENT_PROPERTY_KEY.$CLIENT.client-id") ?: throw Exception("")
        val clientSecret: String = env.getProperty("$CLIENT_PROPERTY_KEY.$CLIENT.client-secret") ?: throw Exception("")
        val registration= CommonOAuth2Provider.GOOGLE.getBuilder(CLIENT)
                .clientId(clientId).clientSecret(clientSecret).build()
        return InMemoryClientRegistrationRepository(registration)
    }

    @Bean
    fun authorizedClientService(): OAuth2AuthorizedClientService {
        return InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository())
    }
}
