package com.bsalon.filters;

import com.bsalon.controllers.commands.RegistrationCommand;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author @bkalika
 */

@WebFilter("/EncodingFilter")
public class EncodingFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(RegistrationCommand.class);

    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) {
        LOGGER.trace(String.format("Start tracing %s#init", EncodingFilter.class));

        encoding = filterConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOGGER.trace(String.format("Start tracing %s#doFilter", EncodingFilter.class));

        String requestEncoding = servletRequest.getCharacterEncoding();

        if(encoding != null && !encoding.equalsIgnoreCase(requestEncoding)) {
            servletRequest.setCharacterEncoding(encoding);
            servletResponse.setCharacterEncoding(encoding);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        LOGGER.trace(String.format("Start tracing %s#destroy", EncodingFilter.class));

        encoding = null;
    }
}
