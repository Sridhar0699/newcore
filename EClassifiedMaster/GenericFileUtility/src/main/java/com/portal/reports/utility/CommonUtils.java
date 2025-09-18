package com.portal.reports.utility;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtils {

	static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);


	/* Convert date to string in given pattern */
	public static String dateFormatter(Date date, String pattern) {

		DateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}

	/* Convert string to required date format */
	public static Date dateFormatter(String stringDate, String pattern) {

		Date date = null;

		SimpleDateFormat simpleDateFormat = null;

		try {

			simpleDateFormat = new SimpleDateFormat(pattern);
			date = simpleDateFormat.parse(stringDate);

		} catch (Exception e) {
			logger.error("Getting error while convert date format");
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

		} catch (Exception exce) {

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
						simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

						try {

							date = simpleDateFormat.parse(stringDate);

						} catch (Exception exceptiona) {
							logger.error("Getting error while convert date format");
						}
					}
				}
			}
		}

		return date;
	}

	/* Generate from date and to date for given financial period */
	public static Map<String, String> generateFinPeriod(String finPeriodDate) {

		Map<String, String> finPeriod = new HashMap<String, String>();

		Date frmPeriod = null;

		SimpleDateFormat simpleDateFormat = null;

		try {

			simpleDateFormat = new SimpleDateFormat("MMyyyy");
			frmPeriod = simpleDateFormat.parse(finPeriodDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(frmPeriod);
			calendar.add(Calendar.MONTH, 1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.add(Calendar.DATE, -1);
			Date toPeriod = calendar.getTime();
			finPeriod.put(dateFormatter(frmPeriod, "yyyy-MM-dd HH:mm:ss"),
					dateFormatter(toPeriod, "yyyy-MM-dd HH:mm:ss"));

		} catch (Exception e) {
			logger.error("Getting error while convert date format");
		}

		return finPeriod;
	}

	/* Generate end date of month for given date */
	public static Date generateToDt(String fromDt) {

		Date toDt = null;

		SimpleDateFormat simpleDateFormat = null;

		try {

			simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(simpleDateFormat.parse(fromDt));
			calendar.add(Calendar.MONTH, 1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.add(Calendar.DATE, -1);
			toDt = calendar.getTime();

		} catch (Exception e) {
			logger.error("Getting error while convert date format");
		}

		return toDt;
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

	/* Generate the stack trace message for logs */
	@SuppressWarnings("rawtypes")
	public static String getStackTrace(Exception ex, Class obj) {

		String stackTraceMsg = null;

		StackTraceElement[] elements = ex.getStackTrace();

		for (int iterator = 1; iterator <= elements.length; iterator++) {

			if (elements[iterator - 1].getClassName().equals(obj.getName())) {

				String className = elements[iterator - 1].getClassName();
				String methodName = elements[iterator - 1].getMethodName();
				int lineNumber = elements[iterator - 1].getLineNumber();
				String excepion = String.valueOf(ex);
				stackTraceMsg = "Exception : " + excepion + " , Class : " + className + " , Method : " + methodName
						+ " , Line number : " + lineNumber;
			}
		}

		return stackTraceMsg;
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

	/* Get upload Checksum value */
	public static String getUploadChksum(String apiKey, String uuid) {
		return md5_sum16(apiKey + uuid);
	}

	/* Get download Checksum value */
	public static String getDownloadChksum(String apiKey, String uuid) {
		return md5_sum16(apiKey + (md5_sum16(apiKey + uuid)));
	}

	/* Get delete Checksum value */
	public static String getDeleteChksum(String apiKey, String uuid) {
		return md5_sum16(apiKey + md5_sum16(apiKey + md5_sum16(apiKey + uuid)));
	}

	/* Convert String into MD5 format */
	private static String md5_sum16(String sData) {

		MessageDigest md;

		try {

			md = MessageDigest.getInstance("MD5");

			return byteArray2Hex(md.digest(sData.getBytes())).substring(0, 16);

		} catch (NoSuchAlgorithmException e) {
			return "NA";
		}
	}

	/* Convert Byte array to hex format */
	private static String byteArray2Hex(final byte[] hash) {

		Formatter formatter = new Formatter();

		for (byte b : hash) {
			formatter.format("%02x", b);
		}

		String result = formatter.toString().toUpperCase();

		formatter.close();

		return result;
	}
	public static <T> List<List<T>> chunks(List<T> input, int chunkSize) {

		int inputSize = input.size();
		int chunkCount = (int) Math.ceil(inputSize / (double) chunkSize);
		int count = 0;

		List<List<T>> chunks = new ArrayList<>(chunkCount);
		List<T> chunk = new ArrayList<>();

		for (int i = 0; i < inputSize; i++) {

			chunk.add(input.get(i));

			count++;

			if (chunk.size() == chunkSize || count == input.size()) {

				chunks.add(chunk);
				chunk = new ArrayList<>();
			}
		}

		return chunks;
	}

	/* Write line into CSV File */
	public static StringBuilder writeLine(List<String> values, char customQuote) {

		boolean first = true;

		String DEFAULT_SEPARATOR = ",";

		StringBuilder sb = new StringBuilder();

		for (String value : values) {

			if (value == null) {
				continue;
			}

			if (!first) {

				sb.append(DEFAULT_SEPARATOR);
			}

			if (customQuote == ' ') {

				sb.append(followCVSformat(value));

			} else {

				sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
			}

			first = false;
		}

		sb.append("\n");

		return sb;
	}

	/* Format the CSV data */
	private static String followCVSformat(String value) {

		String result = value;

		if (result.contains("\"")) {

			result = result.replace("\"", "\"\"");
		}

		return result;
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

	

	@SuppressWarnings("rawtypes")
	public static String getIpAddress() {
		String ipAddress = null;
		try {
			Enumeration nis = NetworkInterface.getNetworkInterfaces();
			while (nis.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) nis.nextElement();
				Enumeration ias = ni.getInetAddresses();
				while (ias.hasMoreElements()) {
					InetAddress ia = (InetAddress) ias.nextElement();

					if (ia.isSiteLocalAddress()) {
						ipAddress = ia.getHostAddress();
					}
				}

			}
		} catch (SocketException ex) {
			logger.error("Exception :", ex);
		}
		return ipAddress;
	}

	public static Map<String, String> convertPeriodsToDates(String fromPeriod, String toPeriod) {
		Map<String, String> dates = new HashMap<String, String>();
		int day = 1;
		if (fromPeriod != null) {
			String fromDate = fromPeriod.substring(2, fromPeriod.length()) + "-" + fromPeriod.substring(0, 2) + "-01";
			String toDate = "";
			Calendar to = Calendar.getInstance();
			if (toPeriod != null) {
				to.set(Integer.parseInt(toPeriod.substring(2, toPeriod.length())),
						Integer.parseInt(toPeriod.substring(0, 2)) - 1, day);
				toDate = toPeriod.substring(2, toPeriod.length()) + "-" + toPeriod.substring(0, 2) + "-"
						+ to.getActualMaximum(Calendar.DAY_OF_MONTH);
			} else {
				to.set(to.get(Calendar.YEAR), to.get(Calendar.MONTH), day);
				toDate = to.get(Calendar.YEAR) + "-" + (to.get(Calendar.MONTH) + 1) + "-"
						+ to.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
			dates.put("fromDate", fromDate);
			dates.put("toDate", toDate);
		}
		return dates;
	}

	public static double round(double amount, double rt, double multiplr, int places) {
		if (places < 0)
			throw new IllegalArgumentException();
		double value = rt * amount * multiplr;
		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	public static List<Integer> getNumbersFromString(String str) {
		List<Integer> numList = new ArrayList<Integer>();
		try {
			Pattern p = Pattern.compile("(\\d+)");
			Matcher m = p.matcher(str);
			while (m.find()) {
				numList.add(Integer.parseInt(m.group(1)));
			}
		} catch (Exception e) {
		}
		return numList;
	}

	public static String getBrowserInfo(String Information) {
		String browsername = "";
		String browserversion = "";
		String browser = Information;
		if (browser.contains("MSIE")) {
			String subsString = browser.substring(browser.indexOf("MSIE"));
			String info[] = (subsString.split(";")[0]).split(" ");
			browsername = info[0];
			browserversion = info[1];
		} else if (browser.contains("Firefox")) {

			String subsString = browser.substring(browser.indexOf("Firefox"));
			String info[] = (subsString.split(" ")[0]).split("/");
			browsername = info[0];
			browserversion = info[1];
		} else if (browser.contains("Chrome")) {

			String subsString = browser.substring(browser.indexOf("Chrome"));
			String info[] = (subsString.split(" ")[0]).split("/");
			browsername = info[0];
			browserversion = info[1];
		} else if (browser.contains("Opera")) {

			String subsString = browser.substring(browser.indexOf("Opera"));
			String info[] = (subsString.split(" ")[0]).split("/");
			browsername = info[0];
			browserversion = info[1];
		} else if (browser.contains("Safari")) {

			String subsString = browser.substring(browser.indexOf("Safari"));
			String info[] = (subsString.split(" ")[0]).split("/");
			browsername = info[0];
			browserversion = info[1];
		}
		return browsername + "-" + browserversion;
	}

	public static String getGstr3bHash(String data) {
		String hash = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] encodedhash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
			hash = CommonUtils.byteArray2Hex(encodedhash);
		} catch (Exception e) {
		}
		return hash;
	}

	/* Convert Object properties into Map for error documents */
	public static Map<String, Object> getErrorInvoiceMap(Object object)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		Map<String, Object> objectMap = new HashMap<String, Object>();

		if (object != null) {

			for (Method m : object.getClass().getMethods()) {
				if (m.getName().startsWith("get") && !m.getName().startsWith("getClass")
						&& !m.getName().startsWith("getItems") && !m.getName().startsWith("getSum")
						&& !m.getName().startsWith("getErr_info") && !m.getName().startsWith("isDocStatus")
						&& !m.getName().startsWith("getDoc_clsf_id") && !m.getName().startsWith("getSply")) {
					Object value = (Object) m.invoke(object);
					objectMap.put(m.getName().substring(3).toLowerCase(), value);
				}
			}
		}

		return objectMap;
	}

	/* Getting Number of dats between two dates */
	public static int getNumberOfDaysBetweenTwoDates(String fromDate, String toDate, String dateFormat) {

		SimpleDateFormat myFormat = new SimpleDateFormat(dateFormat);// "yyyy-MM-dd"
		try {
			Date dateBefore = myFormat.parse(fromDate);
			Date dateAfter = myFormat.parse(toDate);
			long difference = dateAfter.getTime() - dateBefore.getTime();
			float daysBetween = (difference / (1000 * 60 * 60 * 24));
			return (int) daysBetween;
		} catch (Exception e) {
			logger.error("Exception :", e);
		}
		return 0;
	}

	

	public static boolean summaryVersionCheck(String currFin, String conDateStr) {
		Date currDate = null;
		Date conDate = null;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMyyyy");
			currDate = simpleDateFormat.parse(currFin);
			simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			conDate = simpleDateFormat.parse(conDateStr);
		} catch (Exception e) {
		}
		return currDate.compareTo(conDate) >= 0 ? true : false;
	}

	public static Date plusMinusDays(int days) {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days);

		return cal.getTime();
	}

	public static String getMonthNameFromFinPeriod(String finPeriod) {
		String fpWithMonthName = finPeriod;
		try {
			if (finPeriod != null && !finPeriod.trim().isEmpty()) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMyyyy");
				Date date = null;
				date = simpleDateFormat.parse(finPeriod);
				SimpleDateFormat finalFormat = new SimpleDateFormat("MMM yyyy");
				fpWithMonthName = finalFormat.format(date);
			} else {
				fpWithMonthName = "";
			}
		} catch (Exception e) {
			logger.error("Exception :", e);
		}
		return fpWithMonthName;
	}

	public static List<String> getQuarterlyFinPeriods(String retPeriod) {
		List<String> finperiods = new ArrayList<String>();
		try {
			int finM = Integer.parseInt(retPeriod.substring(0, 2));
			int finY = Integer.parseInt(retPeriod.substring(2, 6));
			int startMonth = 1;
			switch (finM) {
			case 13:
				startMonth = 4;
				break;
			case 14:
				startMonth = 7;
				break;
			case 15:
				startMonth = 10;
				break;
			case 16:
				startMonth = 1;
				break;
			}
			if (startMonth == 1) {
				finY = finY + 1;
			}
			for (int i = startMonth; i < startMonth + 3; i++) {
				String fp = i + "" + finY;
				if (fp.length() == 5) {
					finperiods.add("0" + fp);
				} else {
					finperiods.add(fp);
				}
			}
		} catch (Exception e) {
			logger.error("Exception :", e);
		}
		return finperiods;
	}

	@SuppressWarnings("unused")
	private static String byteArray2HexReqular(final byte[] hash) {

		Formatter formatter = new Formatter();

		for (byte b : hash) {
			formatter.format("%02x", b);
		}

		String result = formatter.toString();

		formatter.close();

		return result;
	}

	public static String getCommaSeparatedStringByList(List<String> list) {

		String finalString = "";

		for (String str : list) {

			if (finalString.isEmpty())
				finalString = finalString + str;
			else
				finalString = finalString + "," + str;
		}
		return finalString;
	}

	public static List<Date> getDatesBetweenTwoDates(Date startDate, Date endDate) {
		List<Date> datesInRange = new ArrayList<>();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(startDate);

		Calendar endCalendar = new GregorianCalendar();
		endCalendar.setTime(endDate);

		while (calendar.before(endCalendar)) {
			Date result = calendar.getTime();
			datesInRange.add(result);
			calendar.add(Calendar.DATE, 1);
		}
		Date result = calendar.getTime();
		datesInRange.add(result);
		return datesInRange;
	}

	public static List<String> getFinPeriodsByReconFinperiod(String finPeriod) {
		List<String> finPeriods = new ArrayList<String>();
		try {
			if (finPeriod != null && !finPeriod.trim().isEmpty()) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMyyyy");
				Date date = null;
				date = simpleDateFormat.parse(finPeriod);
				for (int i = 0; i < 12; i++) {
					finPeriods.add(simpleDateFormat.format(date));
					Calendar c = Calendar.getInstance();
					c.setTime(date);
					c.add(Calendar.MONTH, -1);
					date = c.getTime();
				}
			}
		} catch (Exception e) {
			logger.error("Exception :", e);
		}
		return finPeriods;
	}

	public static List<String> getFinPeriodsByFinYear(String finYear) {
		List<String> finyearList = new ArrayList<>();
		String[] arr = new String[] { "04", "05", "06", "07", "08", "09", "10", "11", "12", "01", "02", "03" };
		for (int i = 0; i < arr.length; i++) {
			String[] split = finYear.split("-");
			if (i <= 8) {
				finyearList.add(arr[i] + split[0]);
			} else {
				finyearList.add(arr[i] + split[1]);
			}
		}
		return finyearList;
	}

	
	public static boolean stringMatcher(String s1, String s2) {
		if (s1.matches("(.*)" + s2 + "(.*)") || s2.matches("(.*)" + s1 + "(.*)")) {
			return true;
		} else if (s1.toLowerCase().matches("(.*)" + s2.toLowerCase() + "(.*)")
				|| s2.toLowerCase().matches("(.*)" + s1.toLowerCase() + "(.*)")) {
			return true;
		} else if (s1.replaceAll("[^a-zA-Z0-9]", "").toLowerCase()
				.matches("(.*)" + s2.replaceAll("[^a-zA-Z0-9]", "").toLowerCase() + "(.*)")
				|| s2.replaceAll("[^a-zA-Z0-9]", "").toLowerCase()
						.matches("(.*)" + s1.replaceAll("[^a-zA-Z0-9]", "").toLowerCase() + "(.*)")) {
			return true;
		} else {
			try {
				if (Long.parseLong(s1.trim()) == Long.parseLong(s2.trim())) {
					return true;
				}
			} catch (Exception e) {
			}
		}
		return false;
	}


	public static Map<String, String> getCumilativeCalFinPeriods(String reconFp) {
		Map<String, String> cumFinPeriods = new HashMap<String, String>();
		try {
			if (reconFp != null && !reconFp.trim().isEmpty()) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMyyyy");
				Date date = null;
				date = simpleDateFormat.parse(reconFp);
				for (int i = 0; i < 12; i++) {
					Calendar c = Calendar.getInstance();
					c.setTime(date);
					c.add(Calendar.MONTH, -1);
					if (i < 11) {
						cumFinPeriods.put(simpleDateFormat.format(date), simpleDateFormat.format(c.getTime()));
					} else {
						cumFinPeriods.put(simpleDateFormat.format(date), simpleDateFormat.format(date));
					}
					date = c.getTime();
				}
			}
		} catch (Exception e) {
			logger.error("Exception :", e);
		}
		return cumFinPeriods;
	}

	public static Map<String, String> getMonthNameFromListFinPeriod(List<String> finPeriods) {
		Map<String, String> fpWithMonthNames = new HashMap<String, String>();
		try {
			for (String finPeriod : finPeriods) {
				String fpWithMonthName = "";
				if (finPeriod != null && !finPeriod.trim().isEmpty()) {
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMyyyy");
					Date date = null;
					date = simpleDateFormat.parse(finPeriod);
					SimpleDateFormat finalFormat = new SimpleDateFormat("MMM yyyy");
					fpWithMonthName = finalFormat.format(date);
				} else {
					fpWithMonthName = "";
				}
				fpWithMonthNames.put(finPeriod, fpWithMonthName);
			}
		} catch (Exception e) {
			logger.error("Exception :", e);
		}
		return fpWithMonthNames;
	}

	public static Date plusMinusDays(Date date, int days) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}
	
	public static String formattedName(String firstName, String lastName) {
		String formattedName = "";
		try {
			if (firstName != null && !firstName.isEmpty() && !"null".equalsIgnoreCase(firstName))
				formattedName = firstName;
			if (lastName != null && !lastName.isEmpty() && !"null".equalsIgnoreCase(lastName))
				formattedName = formattedName + " " + lastName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return formattedName;
	}
}
