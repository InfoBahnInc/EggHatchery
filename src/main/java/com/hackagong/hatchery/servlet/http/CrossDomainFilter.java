package com.hackagong.hatchery.servlet.http;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class CrossDomainFilter implements Filter {

	@Override
	public void init(FilterConfig config) throws ServletException {

		// Do nothing
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletResponse httpResponse = (HttpServletResponse) response;		
		httpResponse.setHeader( "Access-Control-Allow-Origin", "http://localhost" );
		httpResponse.setHeader( "Access-Control-Allow-Headers", "Content-Type" );
		httpResponse.setHeader( "Access-Control-Allow-Methods", "GET, PUT" );
		
		chain.doFilter(request, response);		
	}

	@Override
	public void destroy() {

		// Do nothing
	}
}