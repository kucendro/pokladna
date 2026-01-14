package pokladna.shared;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ErrorLogger {
    private static final String LOG_FILE = "store/error.log";
    private static ErrorLogger instance;

    private ErrorLogger() {
        File logFile = new File(LOG_FILE);
        logFile.getParentFile().mkdirs();
    }

    public static ErrorLogger getInstance() {
        if (instance == null) {
            instance = new ErrorLogger();
        }
        return instance;
    }

    public void log(String message, Exception e) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            writer.write("=== " + sdf.format(new Date()) + " ===\n");
            writer.write("Message: " + message + "\n");
            if (e != null) {
                writer.write("Exception: " + e.getClass().getName() + "\n");
                writer.write("Detail: " + e.getMessage() + "\n");
                e.printStackTrace(new PrintWriter(writer));
            }
            writer.write("\n");
        } catch (IOException ex) {
            System.err.println("Failed to write to log: " + ex.getMessage());
        }
    }

    public void log(String message) {
        log(message, null);
    }
}
