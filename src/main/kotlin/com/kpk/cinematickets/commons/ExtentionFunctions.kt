package com.kpk.cinematickets.commons

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken

fun OAuth2AuthenticationToken.clientEmail(): String {
    return this.principal.attributes["email"] as String
}
