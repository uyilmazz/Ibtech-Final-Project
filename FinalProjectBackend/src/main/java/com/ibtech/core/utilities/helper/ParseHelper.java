package com.ibtech.core.utilities.helper;

public class ParseHelper {
	
	public static boolean isInteger(String str) {
		try {
	        Integer.parseInt(str);
	        return true;
	    } catch (NumberFormatException nfe) {}
	    return false;
	}

	public static boolean isLong(String str) {
		try {
	        Long.parseLong(str);
	        return true;
	    } catch (NumberFormatException nfe) {}
	    return false;
	}
}
