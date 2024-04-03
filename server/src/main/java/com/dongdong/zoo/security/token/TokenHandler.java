package com.dongdong.zoo.security.token;

import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.Authentication;

public interface TokenHandler {

	TokenDto generateToken(Authentication authentication);

	boolean validateToken(String token) throws CredentialsExpiredException;

	String extractToken(String header);

	Authentication getAuthentication(String token);

	<T> T getId(String token, Class<T> aClass);

	long getExpirationMillis(String token);

	boolean isAccessToken(String token);
}