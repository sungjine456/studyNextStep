package core.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.controller.UserSessionUtils;

@WebFilter(urlPatterns={"/qna/form", "/qna/create"})
public class LoginCheckFilter implements Filter {
	private static final Logger log = LoggerFactory.getLogger(LoginCheckFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		log.debug("login check filter doFilter");
		HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;
		if (!UserSessionUtils.isLogined(httpServletRequest.getSession())) {
			httpServletResponse.sendRedirect("/users/loginForm");
			return;
        } else {
        	chain.doFilter(servletRequest, servletResponse);
        }
	}

	@Override
	public void destroy() {
	}
}
