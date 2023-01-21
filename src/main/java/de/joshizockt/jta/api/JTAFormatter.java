package de.joshizockt.jta.api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class JTAFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return String.format(
                "%s >> [%s] [%s] %s%n",
                record.getLoggerName(), format.format(new Date(record.getMillis())), record.getLevel(), record.getMessage()
        );
    }

}
