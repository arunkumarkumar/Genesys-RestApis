package com.api.ivr.log4jmask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class CvvOrPinMasker {
	  static final Pattern MASKPATTERN = Pattern.compile("\\s\\d{6}\\s|\\s\\d{4}\\s|\\s\\d{3}\\s");
	  
	  static final Pattern CVV2PATTERN = Pattern.compile("(?s)(<CVV2>(.*)</CVV2>)");
	  
	  public static String maskPIN(String text) {
	    Matcher m = MASKPATTERN.matcher(text);
	    StringBuffer sb = new StringBuffer();
	    while (m.find())
	      m.appendReplacement(sb, (new String(new char[m.group().trim().length()])).replace("\000", "X")); 
	    m.appendTail(sb);
	    return sb.toString();
	  }
	  
	  public static String maskCVV2XML(String text) {
	    Matcher m = CVV2PATTERN.matcher(text);
	    StringBuffer sb = new StringBuffer();
	    if (m.find())
	      m.appendReplacement(sb, "<CVV2>XXX</CVV2>"); 
	    m.appendTail(sb);
	    return sb.toString();
	  }

}
