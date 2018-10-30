package com.apicalls.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerMain {
    private static Logger logger = null;
    private static String logFileName = "Marketplace_Data.log";
    private static boolean debugMode;
    private static final DateFormat df = new SimpleDateFormat("MMM dd,yyyy hh:mm:ss.SSS");

    private static void initializeRepLogger() {
        class MyFormatter extends Formatter {

            @Override
            public String format(LogRecord record) {

                StringBuilder builder = new StringBuilder(1000);
                builder.append(record.getLevel()).append(" - ");
                builder.append(df.format(new Date(record.getMillis()))).append(" - ");
                builder.append("[").append(record.getSourceClassName()).append(".");
                builder.append(record.getSourceMethodName()).append("] - ");
                builder.append(formatMessage(record));
                builder.append("\n");

                return builder.toString();
            }
        }

        try {
            FileHandler fileHandler = new FileHandler(logFileName,true);
            if (debugMode) {
                logger.setLevel(Level.FINER);
                fileHandler.setFormatter(new SimpleFormatter());
            } else {
                logger.setLevel(Level.INFO);
                fileHandler.setFormatter(new MyFormatter());
            }
            Handler handlers[] = logger.getHandlers();
            for (Handler handler : handlers)
                logger.removeHandler(handler);

            logger.setUseParentHandlers(false);
            logger.addHandler(fileHandler);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static Logger getLogger(String logFilename, boolean debug) {
        if (logger == null) {
            if (!logFilename.isEmpty())
                logFileName = logFilename;

            debugMode = debug;
            logger = Logger.getLogger(LoggerMain.class.getName());
            initializeRepLogger();
        }
        return logger;
    }
}
