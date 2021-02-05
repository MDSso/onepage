package com.mds.prj.util;


public class Xss {
	public String Xssf(String message) {
		message = message.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
		message = message.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
		message = message.replaceAll("'", "& #39;");
		message = message.replaceAll("eval\\((.*)\\)", "");
		message = message.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		message = message.replaceAll("script", "");
		return message;
	}
}
 