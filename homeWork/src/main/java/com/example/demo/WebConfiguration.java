package com.example.demo;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class WebConfiguration implements Filter {

	    private static final Logger logger = LogManager.getLogger(HomeWorkApplication.class);

	
    @Override
    public void doFilter(ServletRequest req, ServletResponse res,FilterChain chain) throws IOException, ServletException {

      logger.debug("==========in filter============");
      HttpServletResponse response = (HttpServletResponse) res;
      response.setHeader("Access-Control-Allow-Origin", "*");
      response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT");
      response.setHeader("Access-Control-Max-Age", "3600");
      response.setHeader("Access-Control-Allow-Headers", "Accept, Content-Type,Content-Disposition, Access-Control-Allow-Headers, Authorization, X-Requested-With,X-Msg"); 
      response.setHeader("Access-Control-Expose-Headers","Accept, Content-Type, Content-Disposition,Error-Msg,X-Msg");
      chain.doFilter(req, response);
	      logger.debug("==========End filter============");
    }

    @Override
    public void destroy() {}

    @Override
    public void init(FilterConfig arg0) throws ServletException {}
	
	
}
