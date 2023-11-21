package com.api.ivr.log4jmask;

public class MainMasker {
	 public static String mask(String text) {
		    text = CreditcardMasker.maskCreditCard(text);
		    text = CvvOrPinMasker.maskPIN(text);
		    text = CvvOrPinMasker.maskCVV2XML(text);
		    return text;
		  }

}
