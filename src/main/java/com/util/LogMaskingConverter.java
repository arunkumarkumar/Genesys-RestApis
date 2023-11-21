package com.util;

import com.api.ivr.log4jmask.*;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;

@Plugin(name = "LogMaskingConverter", category = "Converter")
@ConverterKeys({"spi"})
public class LogMaskingConverter extends LogEventPatternConverter {
  protected LogMaskingConverter(String name, String style) {
    super(name, style);
  }
  
  public static LogMaskingConverter newInstance(String[] options) {
    return new LogMaskingConverter("spi", Thread.currentThread().getName());
  }
  
  public void format(LogEvent event, StringBuilder outputMessage) {
    String message = event.getMessage().getFormattedMessage();
    String maskedMessage = message;
    String loggerName = event.getLoggerName();
    try {
      maskedMessage = MainMasker.mask(message);
    } catch (Exception e) {
      System.out.println("Failed While Masking");
      maskedMessage = message;
    } 
    outputMessage.append(maskedMessage);
  }
}