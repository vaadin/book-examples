package com.vaadin.book;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public final class LogFormatter extends Formatter {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");

    @Override
    public String format(LogRecord record) {
        System.out.println("Logging...");
        StringBuilder sb = new StringBuilder();

        sb.append(df.format(new Date(record.getMillis())))
            .append(" ")
            .append(record.getLevel().getLocalizedName())
            .append(": ")
            .append(formatMessage(record));

        if (record.getThrown() != null) {
            try {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                record.getThrown().printStackTrace(pw);
                pw.close();
                sb.append(sw.toString());
            } catch (Exception ex) {
                // ignore
            }
        }

        return sb.toString();    }
}
