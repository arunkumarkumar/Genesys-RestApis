package com.util;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.servlet.ServletContext;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.appender.FileManager;
import org.apache.logging.log4j.core.async.AsyncLoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.message.FormattedMessageFactory;
import org.apache.logging.log4j.message.MessageFactory;

public class CustomLogger {
	public static final String FILE_EXTENSION = ".log";
	  
	  private static final Map<String, Logger> logCache = new HashMap<>();
	  
	  static final LoggerContext ctx = (LoggerContext)LogManager.getContext(false);
	  
	  public static Logger getLogger(String UCID) {
	    Logger logger = logCache.get(UCID);
	    System.out.println("logger : " + logger);
	    System.out.println("Config :- " + ctx.getConfiguration());
	    System.out.println("Config Location :- " + ctx.getConfigLocation());
	    if (System.getProperty("log4jpath") == null) {
	      System.out.println("log4jpath not defined at Load on Servlet..");
	      return null;
	    } 
	    if (logger == null) {
	      AsyncLoggerContext asyncLoggerContext = new AsyncLoggerContext(UCID);
	      FormattedMessageFactory formattedMessageFactory = new FormattedMessageFactory();
	      logger = new SimpleLogger((LoggerContext)asyncLoggerContext, UCID, (MessageFactory)formattedMessageFactory);
	      PatternLayout layout1 = PatternLayout.newBuilder()
	        .withConfiguration(ctx.getConfiguration())
	        .withPattern("%d{dd/MM/yyyy HH:mm:ss:SSS} %5p - %m%n").build();
	      ConsoleAppender consoleApp = ((ConsoleAppender.Builder)((ConsoleAppender.Builder)((ConsoleAppender.Builder)ConsoleAppender.newBuilder()
	        .withName(UCID))
	        .withName("Console-" + UCID))
	        .withLayout((Layout)layout1))
	        .build();
	      consoleApp.start();
	      PatternLayout layout2 = PatternLayout.newBuilder()
	        .withConfiguration(ctx.getConfiguration())
	        .withPattern("%d{dd/MM/yyyy HH:mm:ss:SSS} %5p - %m%n").build();
	      System.out.println("log4jpath : " + System.getProperty("log4jpath"));
	      FileAppender fileAppender = ((FileAppender.Builder)((FileAppender.Builder)FileAppender.newBuilder()
	        .withLayout((Layout)layout2))
	        .withFileName(String.valueOf(String.valueOf(System.getProperty("log4jpath"))) + File.separator + "sessionid" + File.separator + (new SimpleDateFormat("yyyy/MM/dd/")).format(new Date()) + UCID + ".log")
	        .withName(UCID))
	        .build();
	      fileAppender.start();
	      logger.getContext().getConfiguration().getLoggerConfig("root").addAppender((Appender)fileAppender, Level.ALL, null);
	      logger.getContext().getConfiguration().getLoggerConfig("root").addAppender((Appender)consoleApp, Level.ALL, null);
	      logCache.put(UCID, logger);
	    } 
	    System.out.println("Appenders : " + logger.getContext().getConfiguration().getLoggerConfig(UCID).getAppenders());
	    for (Map.Entry<String, Appender> entry : (Iterable<Map.Entry<String, Appender>>)logger.getContext().getConfiguration().getLoggerConfig(UCID).getAppenders().entrySet()) {
	      if (((String)entry.getKey()).startsWith("DefaultConsole"))
	        logger.getContext().getConfiguration().getLoggerConfig(UCID).removeAppender(entry.getKey()); 
	    } 
	    logger.getContext().updateLoggers();
	    return logger;
	  }
	  
	  public static Logger getLogger(String UCID, LoggerContext loggerContext) {
	    Logger logger = logCache.get(UCID);
	    System.out.println("UCID -: " + UCID);
	    System.out.println("logger -: " + logger);
	    System.out.println("Config -: " + loggerContext.getConfiguration());
	    System.out.println("Config Location : " + loggerContext.getConfigLocation());
	    System.out.println("Log4J Path : " + System.getProperty("log4jpath"));
	    if (System.getProperty("log4jpath") == null) {
	      System.out.println("log4jpath not defined at Load on Servlet..");
	      return null;
	    } 
	    if (logger == null) {
	      System.out.println("Logger is null");
	      AsyncLoggerContext asyncLoggerContext = new AsyncLoggerContext(UCID);
	      FormattedMessageFactory formattedMessageFactory = new FormattedMessageFactory();
	      logger = new SimpleLogger((LoggerContext)asyncLoggerContext, UCID, (MessageFactory)formattedMessageFactory);
	      PatternLayout layout1 = PatternLayout.newBuilder()
	        .withConfiguration(loggerContext.getConfiguration())
	        .withPattern("%d{dd/MM/yyyy HH:mm:ss:SSS} %5p -  %ex{full} %spi %n").build();
	      ConsoleAppender consoleApp = ((ConsoleAppender.Builder)((ConsoleAppender.Builder)((ConsoleAppender.Builder)ConsoleAppender.newBuilder()
	        .withName(UCID))
	        .withName("Console" + UCID))
	        .withLayout((Layout)layout1))
	        .build();
	      System.out.println("before console App ");
	      consoleApp.start();
	      PatternLayout layout2 = PatternLayout.newBuilder()
	        .withConfiguration(loggerContext.getConfiguration())
	        .withPattern("%d{dd/MM/yyyy HH:mm:ss:SSS} %5p - %ex{full} %spi %n").build();
	      System.out.println("log4jpath : " + System.getProperty("log4jpath"));
	      FileAppender fileAppender = ((FileAppender.Builder)((FileAppender.Builder)FileAppender.newBuilder()
	        .withLayout((Layout)layout2))
	        .withFileName(String.valueOf(String.valueOf(System.getProperty("log4jpath"))) + File.separator + "sessionid" + File.separator + (new SimpleDateFormat("yyyy/MM/dd/")).format(new Date()) + UCID + ".log")
	        .withName(UCID))
	        .build();
	      fileAppender.start();
	      logger.getContext().getConfiguration().getLoggerConfig("root").addAppender((Appender)fileAppender, Level.ALL, null);
	      logger.getContext().getConfiguration().getLoggerConfig("root").addAppender((Appender)consoleApp, Level.ALL, null);
	      logCache.put(UCID, logger);
	    } 
	    System.out.println("Appenders : " + logger.getContext().getConfiguration().getLoggerConfig(UCID).getAppenders());
	    for (Map.Entry<String, Appender> entry : (Iterable<Map.Entry<String, Appender>>)logger.getContext().getConfiguration().getLoggerConfig(UCID).getAppenders().entrySet()) {
	      if (((String)entry.getKey()).startsWith("DefaultConsole"))
	        logger.getContext().getConfiguration().getLoggerConfig(UCID).removeAppender(entry.getKey()); 
	    } 
	    logger.getContext().updateLoggers();
	    return logger;
	  }
	  
	  private static class SimpleLogger extends Logger {
	    public SimpleLogger(LoggerContext context, String UCID, MessageFactory msgFactory) {
	      super(context, UCID, msgFactory);
	      setLevel(Level.ALL);
	    }
	  }
	  
	  public static void initialize(ServletContext servletContext) throws URISyntaxException {
	    System.out.println("initialize");
	    String log4JconfigLocation = servletContext.getRealPath("/data/ddlog4j2.xml");
	    File log4JconfigFile = new File(log4JconfigLocation);
	    URI configURI = log4JconfigFile.toURI();
	    ctx.setConfigLocation(configURI);
	    System.out.println("Log4J2 Config Location @ initialize : " + ctx.getConfigLocation());
	  }
	  
	  public static void removeAppender(String UCID) {
	    Logger logger = logCache.get(UCID);
	    if (logger != null) {
	      LoggerContext context = logger.getContext();
	      Configuration configuration = context.getConfiguration();
	      LoggerConfig loggerConfig = configuration.getLoggerConfig(UCID);
	      System.out.println("Config Appenders :" + configuration.getAppenders());
	      System.out.println("Get Appenders : " + loggerConfig.getAppenders());
	      for (Map.Entry<String, Appender> entry : (Iterable<Map.Entry<String, Appender>>)loggerConfig.getAppenders().entrySet()) {
	        if (((String)entry.getKey()).startsWith("Console")) {
	          ConsoleAppender consoleAppender = (ConsoleAppender)entry.getValue();
	          consoleAppender.getManager().close();
	          loggerConfig.removeAppender(entry.getKey());
	          continue;
	        } 
	        FileAppender fileAppender = (FileAppender)entry.getValue();
	        ((FileManager)fileAppender.getManager()).close();
	        loggerConfig.removeAppender(entry.getKey());
	      } 
	      loggerConfig.stop();
	      System.out.println("Get Appenders : " + loggerConfig.getAppenders());
	      context.updateLoggers();
	    } 
	    logCache.remove(UCID);
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
	    Logger debugLogger = getLogger(sessionID, context);
	    logCache.put(sessionID, debugLogger);
	    debugLogger.debug(" TPIN : 1234 ");
	    String className = (new Object() {
	      
	      }).getClass().getEnclosingClass().getName();
	    System.out.println("Currently running Java file: " + className);
	  }

}
