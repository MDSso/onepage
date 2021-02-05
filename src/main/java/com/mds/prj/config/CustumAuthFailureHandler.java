package com.mds.prj.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;

@Service
public class CustumAuthFailureHandler implements AuthenticationFailureHandler {
	
	private final String defaultFailureUrl = "/loginForm";
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
			request.setAttribute("errorMessage", "�α��ο� �����߽��ϴ�. �Է� ������ Ȯ���� �ּ���.");
			request.getRequestDispatcher(defaultFailureUrl).forward(request, response);
	}
	

}
