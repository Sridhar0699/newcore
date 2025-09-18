package com.portal.security.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class CommonUtils {

	static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);

	public static Integer getUserTypeId() {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		Integer userTypeId = (request.getHeader("userTypeId") != null && !request.getHeader("userTypeId").isEmpty())
				? Integer.parseInt(request.getHeader("userTypeId")) : null;
		if (userTypeId == null) {
			userTypeId = request.getParameterMap().get("userTypeId") != null
					? Integer.parseInt((String) request.getParameterMap().get("userTypeId")) : null;
		}
		return userTypeId;
	}

	public static Date formatDate(String dateString) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			if (dateString != null && !"".equalsIgnoreCase(dateString))
				date = sdf.parse(dateString);
		} catch (Exception e) {
			logger.error("Exception :" + ExceptionUtils.getStackTrace(e));
		}
		return date;
	}

	/* Convert string to required date format */
	public static Date dateFormatter(String stringDate) {

		Date date = null;

		SimpleDateFormat simpleDateFormat = null;

		try {

			simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = simpleDateFormat.parse(stringDate);

		} catch (Exception e) {

			simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy");

			try {

				date = simpleDateFormat.parse(stringDate);

			} catch (Exception ex) {

				simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

				try {

					date = simpleDateFormat.parse(stringDate);

				} catch (Exception exc) {

					simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

					try {

						date = simpleDateFormat.parse(stringDate);

					} catch (Exception exception) {

						logger.error("Getting error while convert date format");
					}
				}
			}
		}

		return date;
	}
	
	public static String dateFormatter(Date date, String pattern) {

		DateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}
}