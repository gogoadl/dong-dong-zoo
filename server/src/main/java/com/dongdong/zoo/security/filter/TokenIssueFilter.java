package com.dongdong.zoo.security.filter;

import com.dongdong.zoo.security.principal.CustomUserDetails;
import com.dongdong.zoo.security.service.TokenManagementService;
import com.dongdong.zoo.security.token.TokenDto;
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
import java.io.PrintWriter;
import java.util.List;

@RequiredArgsConstructor
public class TokenIssueFilter extends OncePerRequestFilter {

	private final TokenHandler tokenHandler;
	private final TokenManagementService tokenManagementService;
	private final List<String> patterns;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();

		TokenDto tokenDto = null;

		if (authentication.isAuthenticated()) {
			tokenDto = tokenHandler.generateToken(authentication);
		}

		filterChain.doFilter(request, response);

		if (tokenDto != null) {
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
			tokenManagementService.saveRefreshToken(userDetails.getUserId().toString(), tokenDto.getRefreshToken());

			// PrintWriter writer = response.getWriter();
			// writer.write(tokenDto.toString()); // todo: not just string

			//            response.setHeader("Authorization", "Bearer " + );
		}
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
