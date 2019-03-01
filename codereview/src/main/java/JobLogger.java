import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JobLogger {
    private boolean logToFile;
    private boolean logToConsole;
    private boolean logMessage;
    private boolean logWarning;
    private boolean logError;
    private boolean logToDatabase;
    private Map dbParams;
    private Logger logger;

    public JobLogger(boolean logToFileParam, boolean logToConsoleParam, boolean logToDatabaseParam,
                     boolean logMessageParam, boolean logWarningParam, boolean logErrorParam, Map
                             dbParamsMap) {
        logger = Logger.getLogger("MyLog");
        logError = logErrorParam;
        logMessage = logMessageParam;
        logWarning = logWarningParam;
        logToDatabase = logToDatabaseParam;
        logToFile = logToFileParam;
        logToConsole = logToConsoleParam;
        dbParams = dbParamsMap;
    }

    public void LogMessage(String messageText, boolean message, boolean warning, boolean error)
            throws Exception {
        int idCaseLogging = 0;
        File logFile = new File(dbParams.get("logFileFolder") + "/logFile.txt");
        Properties connectionProps = new Properties();
        Connection connection = DriverManager.getConnection("jdbc:" + dbParams.get("dbms") + "://" +
                dbParams.get("serverName")
                + ":" + dbParams.get("portNumber") + "/", connectionProps);
        Statement stmt = connection.createStatement();
        FileHandler fh = new FileHandler(dbParams.get("logFileFolder") + "/logFile.txt");
        ConsoleHandler ch = new ConsoleHandler();

        messageText.trim();

        if (!logToConsole && !logToFile && !logToDatabase) {
            throw new Exception("Invalid configuration");
        }
        if ((!logError && !logMessage && !logWarning) || (!warning && !error)) {
            throw new Exception("Error or Warning or Message must be specified");
        }

        connectionProps.put("user", dbParams.get("userName"));
        connectionProps.put("password", dbParams.get("password"));

        if (message && logMessage) idCaseLogging = 1;
        else if (error && logError) idCaseLogging = 2;
        else if (warning && logWarning) idCaseLogging = 3;


        if (!logFile.exists())
            logFile.createNewFile();

        if (error && logError)
            logger.info("error " + DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) + messageText);
        else if (warning && logWarning)
            logger.warning("warning " + DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) + messageText);
        else if (message && logMessage)
            logger.info("message " + DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) +
                    messageText);

        if (logToFile) {
            logger.addHandler(fh);
            logger.log(Level.INFO, messageText);
        }
        if (logToConsole) {
            logger.addHandler(ch);
            logger.log(Level.INFO, messageText);
        }
        if (logToDatabase) {
            stmt.executeUpdate("insert into Log_Values('" + message + "', " + String.valueOf(idCaseLogging) + ")");
        }
    }
}
