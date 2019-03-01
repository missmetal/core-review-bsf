import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.Statement;

import java.sql.Connection;

import static org.junit.Assert.assertTrue;

public class JobLoggerTest {

    @Mock
    private Connection mockConnection;
    @Mock
    private Statement mockStatement;

    @Before
    public void onSetup() throws Exception {
        Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
        Mockito.when(mockConnection.createStatement());
    }

    @Test
    public void testIsMessageOk() throws Exception {
        JobLogger jobLogger = new JobLogger(false, true, false, true, false, false, null);

        jobLogger.LogMessage("this is a test OK", true, false, false);
    }

    @Test
    public void testExceptionWarning() throws Exception {
        JobLogger jobLogger = new JobLogger(false, false, false, true, false, false, null);

        jobLogger.LogMessage("this is a test with error in warning", true, false, false);
    }

    @Test
    public void testExceptionError() {
    }
}
