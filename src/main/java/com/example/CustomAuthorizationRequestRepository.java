/**
	Aug 30, 2019
	deepakk
 */
package com.example;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author deepakk
 *
 */
@Component
public class CustomAuthorizationRequestRepository
		implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

	private Map<String, OAuth2AuthorizationRequest> authReqMap = new HashMap<>();

	@Override
	public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
		String stateParameter = this.getStateParameter(request);
		if (stateParameter == null) {
			return null;
		}
		return authReqMap.get(stateParameter);
	}

	@Override
	public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request,
			HttpServletResponse response) {
		if (authorizationRequest == null) {
			this.removeAuthorizationRequest(request, response);
			return;
		}
		String state = authorizationRequest.getState();
		Assert.hasText(state, "authorizationRequest.state cannot be empty");
		authReqMap.put(state, authorizationRequest);
	}

	@Override
	public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) {
		Assert.notNull(request, "request cannot be null");
		String stateParameter = this.getStateParameter(request);
		if (stateParameter == null) {
			return null;
		}
		OAuth2AuthorizationRequest originalRequest = authReqMap.remove(stateParameter);
		if (!authReqMap.isEmpty()) {
			authReqMap.put(stateParameter, originalRequest);
		} else {
			authReqMap.remove(stateParameter);
		}
		return originalRequest;
	}

	private String getStateParameter(HttpServletRequest request) {
		return request.getParameter(OAuth2ParameterNames.STATE);
	}

}
