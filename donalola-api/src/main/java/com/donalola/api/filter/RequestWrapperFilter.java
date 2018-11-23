
package com.donalola.api.filter;


import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public class RequestWrapperFilter extends GenericFilterBean {

    private RequestMatcher matcher;

    public RequestWrapperFilter(RequestMatcher matcher) {
        this.matcher = matcher;
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (matcher != null && matcher.matches(request)) {
            ContentCachingRequestWrapper contentCachingRequestWrapper = new ContentCachingRequestWrapper(request);
            InputStream is = contentCachingRequestWrapper.getInputStream();
            while (is.read() != -1) {
            }
            filterChain.doFilter(contentCachingRequestWrapper, response);
        } else {
            filterChain.doFilter(servletRequest, response);
        }
    }
}
