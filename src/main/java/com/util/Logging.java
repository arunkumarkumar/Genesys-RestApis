package com.util;

import java.io.File;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

public class Logging {
  static final Pattern MASKPATTERN = Pattern.compile("\\b([4-6,9]{1})([0-9]{3})([0-9- ]{8,11})(\\d{4})\\b");
  
  static final Pattern TPINMASKPATTERN = Pattern.compile("\\s\\d{6}\\s|\\s\\d{4}\\s|\\s\\d{3}\\s");
  
  public static Logger LoggerConfiguration(String loggerInstance) {
    File file = new File(System.getProperty("logxmlPath"));
    LoggerContext context = (LoggerContext)LogManager.getContext();
    context.setConfigLocation(file.toURI());
    return (Logger)CustomLogger.getLogger(loggerInstance, context);
  }
  
  public static void main(String[] args) {
    String text = "org.apache.jsp.src.LoadProps_jsp";
    System.out.println("filename : " + text.split("[.]")[4]);
    System.setProperty("logxmlPath", "/Genesys/Composer/log4j2.xml");
    File file = new File(System.getProperty("logxmlPath"));
    System.out.println("file: " + file);
  }
}
