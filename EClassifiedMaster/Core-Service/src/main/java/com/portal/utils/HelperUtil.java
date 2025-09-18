package com.portal.utils;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mashape.unirest.http.Unirest;

public class HelperUtil {

	static final Logger logger = LoggerFactory.getLogger(HelperUtil.class);

	/* Convert date to string in given pattern */
	public static String dateFormatter(Date date, String pattern) {

		String formatedDate = null;

		if (date != null) {

			DateFormat dateFormat = new SimpleDateFormat(pattern);

			formatedDate = dateFormat.format(date);
		}

		return formatedDate;
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

	/* Generate SQL in statements for given list */
	public static String getSqlInStringByList(List<String> list) {

		String finalString = "";

		for (String str : list) {

			if (finalString.isEmpty())
				finalString = finalString + "'" + str + "'";
			else
				finalString = finalString + " , " + "'" + str + "'";
		}

		finalString = finalString + "";

		return finalString;
	}

	/* Convert Object properties into Map */
	public static Map<String, Object> convertObjectToMap(Object obj) {

		Map<String, Object> map = new HashMap<String, Object>();

		try {

			Method[] methods = obj.getClass().getMethods();

			for (Method m : methods) {

				if (m.getName().startsWith("get") && !m.getName().startsWith("getClass")) {

					Object value = (Object) m.invoke(obj);
					map.put(m.getName().substring(3).toLowerCase(), (Object) value);
				}
			}

		} catch (Exception e) {

			logger.error("Getting error while converting object to map.");
		}

		return map;
	}

	public static String getSqlInIntegerByList(List<Integer> list) {

		String finalNumbers = "";

		for (Integer num : list) {

			if (finalNumbers.isEmpty())
				finalNumbers = finalNumbers + num;
			else
				finalNumbers = finalNumbers + " , " + num;
		}

		return finalNumbers;
	}

	/* Get self signed certificate for trust the URL */
	public static void getSelfSignedCertificate() {

		try {

			RequestConfig config = RequestConfig.custom().setConnectTimeout(1800000)
					.setConnectionRequestTimeout(1800000).setSocketTimeout(1800000).build();
			SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext);
			CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf)
					.setDefaultRequestConfig(config).build();
			Unirest.setHttpClient(httpclient);

		} catch (Exception e) {

			logger.error("Getting error while getting self signed certificate..");
		}
	}

	/* Format double value as String */
	public static String formatDoubleValAsString(double val) {

		String frmtVal = null;

		try {

			frmtVal = String.valueOf(BigDecimal.valueOf(val));

		} catch (Exception ex) {

			logger.error("Error is occured while fromat double value as String.." + ex.getMessage());
		}

		return frmtVal;
	}

	/* Get Excel sheet header style */
	public static CellStyle getHeaderStyle(XSSFWorkbook workbook) {

		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setFontHeightInPoints((short) 10);
		//font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style.setFont(font);
		style.setLocked(true);

		return style;
	}

	/* Covert workbook to byte array */
	public static byte[] getWorkBookAsBytes(XSSFWorkbook workbook) throws java.io.IOException {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		workbook.write(bos);
		byte[] data = bos.toByteArray();
		return data;
	}

	/* Get Excel value based on cell type */
	/*public static String getCellValueAsString(XSSFCell cell) {

		String value = null;

		try {

			switch (cell.getCellType()) {

			case XSSFCell.CELL_TYPE_STRING:

				if (cell.getStringCellValue() != null)
					value = cell.getStringCellValue();
				break;

			case XSSFCell.CELL_TYPE_NUMERIC:

				value = NumberToTextConverter.toText(cell.getNumericCellValue());
				break;

			case XSSFCell.CELL_TYPE_BOOLEAN:

				value = String.valueOf(cell.getBooleanCellValue());
				break;
			}

		} catch (Exception ex) {

			logger.error("Exception :", ex);
		}

		return value;
	}
*/
	/* Base64 encoding */
	public static String encodeBase64String(byte[] bytes) {

		String encode = null;

		try {

			encode = new String(java.util.Base64.getEncoder().encode(bytes));

		} catch (Exception e) {

			logger.error("Exception :" + ExceptionUtils.getStackTrace(e));
		}

		return encode;
	}

	/* Base64 decoding */
	public static String decodeBase64String(String value) {

		String decode = null;

		try {

			decode = new String(Base64.getDecoder().decode(value));

		} catch (Exception e) {

			logger.error("Exception :" + ExceptionUtils.getStackTrace(e));
		}

		return decode;
	}

	/* Get Exception message */
	public static String getExceptionMessage(Exception e) {

		String msg = null;

		Throwable te1 = e.getCause();

		if (e.getCause() != null) {

			Throwable te2 = te1.getCause();

			msg = te2 != null ? te2.getMessage() : te1.getMessage();
		}

		return msg;
	}

	/* Get response time of service */
	public static Double getResponseTime(Date from, Date to) {

		Double responseTime = 0.0d;

		try {

			long dt1 = from.getTime(), dt2 = to.getTime(), diff = dt2 - dt1;

			responseTime = diff / 1000.0;

		} catch (Exception e) {
			logger.error("Error while getting response time: " + ExceptionUtils.getStackTrace(e));
		}

		return responseTime;
	}

	/* Nullify all the strings in object */
	public static void nullifyStrings(Object o) {

		for (Field f : o.getClass().getDeclaredFields()) {

			f.setAccessible(true);

			try {

				if (f.getType().equals(String.class)) {

					String value = (String) f.get(o);

					if (value != null && value.trim().isEmpty()) {
						f.set(o, null);
					}
				}

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	/* Add leading zeros based on String length */
	public static String formatWithZero(String val, int len) {

		return val.length() < len ? ("0" + val) : val;
	}

	public static Long rendomNumber() {
		int intVal = 100000000 + new Random().nextInt(900000000);
		Long longVal = (long) intVal;
		if (longVal < 0) {
			longVal = longVal * -1;
		}
		return longVal;
	}

	public static boolean isNullOrEmpty(String str) {
		if (str != null && !str.isEmpty())
			return false;
		return true;
	}

	public static Date formatDate(String dateString) {
		Date date = null;
		dateString=dateString.replace("T", " ").replace("Z", "");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		try {
			date = simpleDateFormat.parse(dateString);
		} catch (Exception e) {
			logger.error("Getting error while convert date format");
		}
		return date;
	}
	
	public static double parseDoubleValue(float floatValue) {
		DecimalFormat df = new DecimalFormat("#.##");
		return Double.parseDouble(df.format(floatValue));
	}
}
