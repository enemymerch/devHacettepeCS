

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Logger {
    private static Logger instance = null;

    private Logger() {
        // Exists only to defeat instantiation.
    }

    public static Logger getInstance() {
        if(instance == null) {
            instance = new Logger();
        }


        if (!Files.exists(Paths.get("run.log"))){
            try {
                Files.createFile(Paths.get("run.log"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return instance;
    }

    public static void log_info(String msg) throws IOException {
        msg += "\n";
        Files.write(Paths.get("run.log"), msg.getBytes(), StandardOpenOption.APPEND);
    }

}
