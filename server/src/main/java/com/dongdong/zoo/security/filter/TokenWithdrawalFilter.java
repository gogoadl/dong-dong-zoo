package com.dongdong.zoo.security.filter;

import com.dongdong.zoo.security.principal.CustomUserDetails;
import com.dongdong.zoo.security.service.TokenManagementService;
import com.dongdong.zoo.security.token.TokenHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class TokenWithdrawalFilter extends OncePerRequestFilter {

	private final TokenHandler tokenHandler;
	private final TokenManagementService tokenManagementService;
	private final List<String> patterns;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String header = request.getHeader("Authorization");
		String token = tokenHandler.extractToken(header);

		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

		// remove refresh token in cache
		tokenManagementService.removeRefreshToken(userDetails.getUserId().toString());

		// save access token in logout list
		tokenManagementService.saveAccessTokenInLogoutList(token);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String servletPath = request.getServletPath();

		for (String pattern : patterns) {
			if (request.getServletPath().equals(pattern)) {
				return false;
			}
		}

		return true;
	}
}
