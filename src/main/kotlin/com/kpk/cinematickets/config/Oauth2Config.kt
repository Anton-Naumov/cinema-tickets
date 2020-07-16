package com.kpk.cinematickets.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider
import org.springframework.security.oauth2.client.JdbcOAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository

@Configuration
class Oauth2Config(
        @Value("\${spring.security.oauth2.client.registration.google.client-id}") val clientId: String,
        @Value("\${spring.security.oauth2.client.registration.google.client-secret}") val clientSecret: String
) {

    @Bean
    fun clientRegistrationRepository(): ClientRegistrationRepository {
        val registration= CommonOAuth2Provider.GOOGLE.getBuilder("google")
                .clientId(clientId).clientSecret(clientSecret).build()
        return InMemoryClientRegistrationRepository(registration)
    }

    @Bean
    fun authorizedClientService(jdbcOperations: JdbcOperations, clientRegistrationRepository: ClientRegistrationRepository): OAuth2AuthorizedClientService {
        return JdbcOAuth2AuthorizedClientService(jdbcOperations, clientRegistrationRepository)
    }

}
