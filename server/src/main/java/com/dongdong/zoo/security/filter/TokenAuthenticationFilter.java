package com.dongdong.zoo.security.filter;

import com.dongdong.zoo.security.service.TokenManagementService;
import com.dongdong.zoo.security.token.TokenHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

	private final TokenHandler tokenHandler;
	private final TokenManagementService tokenManagementService;
	private final List<String> patterns;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String header = request.getHeader("Authorization");
		String token = tokenHandler.extractToken(header);

		if (token == null || !tokenHandler.validateToken(token)) {
			throw new BadCredentialsException("Authentication Failed");
		}

		if (tokenHandler.isAccessToken(token)) { // access token authentication
			if (tokenManagementService.existsAccessTokenInLogoutList(token)) {
				throw new BadCredentialsException("Authentication Failed"); // todo: handle exception
			}

			// todo: if refresh token is not expired

			SecurityContext securityContext = SecurityContextHolder.getContext();
			Authentication authentication = tokenHandler.getAuthentication(token);
			securityContext.setAuthentication(authentication);

		} else { // refresh token authentication
			String id = tokenHandler.getId(token, String.class);
			if (!tokenManagementService.matchesExistingRefreshToken(id, token)) {
				throw new BadCredentialsException("Authentication Failed");
			}

			// todo: getAuthentication; requires authorities
			// todo: use request.setAttribute()?
		}

		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String servletPath = request.getServletPath();

		for (String pattern : patterns) {
			if (servletPath.equals(pattern)) {
				return true;
			}
		}

		return false;
	}
}
