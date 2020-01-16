package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringWebappSecurityOauthSocialApplication {

	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;

	public static void main(String[] args) {
		SpringApplication.run(SpringWebappSecurityOauthSocialApplication.class, args);
	}

	@GetMapping("/")
	public Authentication getAuthentication(OAuth2AuthenticationToken authentication) {
		OAuth2AuthorizedClient client = authorizedClientService
				.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());
		System.out.println(client.getAccessToken().getTokenValue());
		return authentication;
	}

}
