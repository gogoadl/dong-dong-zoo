package com.dongdong.zoo.config.argumentresolver;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.dongdong.zoo.annotation.Login;
import com.dongdong.zoo.security.principal.CustomUserDetails;

public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {

		boolean hasAnnotation = parameter.hasParameterAnnotation(Login.class);
		boolean isAssignable = Long.class.isAssignableFrom(parameter.getParameterType());

		return hasAnnotation & isAssignable;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		if (authentication.isAuthenticated()) {
			CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
			return userDetails.getUserId();
		}

		return null;
	}
}
