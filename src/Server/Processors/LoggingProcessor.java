package Server.Processors;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.util.logging.Logger;

public class LoggingProcessor {
    private final Logger logger;
    private final FileHandler fileHandler;

    public LoggingProcessor(String loggedFileName) throws IOException {
        this.logger = Logger.getLogger("LoggingProcessor");
        this.fileHandler = new FileHandler(loggedFileName);
        this.fileHandler.setFormatter(new SimpleFormatter());
        this.logger.addHandler(this.fileHandler);
    }

    public void log(String message) {
        logger.info(message);
    }

    public void close() {
        this.fileHandler.close();
    }
}