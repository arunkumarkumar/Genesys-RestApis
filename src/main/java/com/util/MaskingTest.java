package com.util;

import java.io.File;
import java.util.Date;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.LoggerContext;

public class MaskingTest {
  private static final Logger logger = LogManager.getLogger(MaskingTest.class);
  
  public static void myTest() {
    System.out.println("logger : " + logger.toString());
    logger.info("this is my info message");
    logger.debug("This is debug message");
    logger.debug("Passed to server::0084USER:17603,IP:0:0:0:0:0:0:0:1,3425,Credit Card 1:1000002367844224,3425,Credit Card2:1000002367844224 , CVV:234,SSN:123456789");
  }
  
  public static void main(String[] args) {
    System.setProperty("log4jpath", "D:\\Genesys\\Composer_new");
    String callID = "123";
    Date timeStamp = new Date();
    Random random = new Random();
    long random16DigitNumber = 1000000000000000L + random.nextLong() % 9000000000000000L;
    String sessionID = (new StringBuilder(String.valueOf(random16DigitNumber))).toString();
    File file = new File("/Genesys/Composer/log4j2.xml");
    LoggerContext context = (LoggerContext)LogManager.getContext();
    context.setConfigLocation(file.toURI());
    System.out.println("started");
    Logger logger = CustomLogger.getLogger(sessionID, context);
    logger.info("this is my info message");
    logger.debug("This is debug message");
    logger.debug("OTP is 123456 ");
    logger.debug("TPIN is 1234 ");
    logger.debug("Passed to server::0084USER:17603,IP:0:0:0:0:0:0:0:1,3425,Credit Card 1: 6011000990139424 ,3425,Credit Card2:1000002367844224 , CVV:234,SSN:123456789");
    System.out.println("completed");
  }
}
