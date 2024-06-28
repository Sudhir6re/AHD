package com.mahait.gov.in;

import javax.servlet.http.HttpServletRequest;

public class Utility {
//	protected final static Log logger = LogFactory.getLog(Utility.class);
	 public static String getSiteURL(HttpServletRequest request) {
	        String siteURL = request.getRequestURL().toString();
//	        logger.info("siteURL "+siteURL);
	        return siteURL.replace(request.getServletPath(), "");
	    }

}
