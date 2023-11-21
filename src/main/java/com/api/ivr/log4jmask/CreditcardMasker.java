package com.api.ivr.log4jmask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class CreditcardMasker {
	  static final Pattern MASKPATTERN = Pattern.compile("\\b([4-6,9]{1})([0-9]{3})([0-9- ]{8,11})(\\d{4})\\b");
	  
	  public static String maskCreditCard(String text) {
	    String MASKCARD = "$1$2XXXXXX$4";
	    Matcher matcher = MASKPATTERN.matcher(text);
	    if (matcher.find())
	      return matcher.replaceAll("$1$2XXXXXX$4"); 
	    return text;
	  }
	  
	  public static void main(String[] args) {
	    System.out.println(maskCreditCard("cc 4012888888881881 "));
	  }
	}

