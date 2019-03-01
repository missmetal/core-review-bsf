import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main (String args[]){
         Map<Integer,String> paramsMap = new HashMap<Integer, String>();

         JobLogger jobLogger = new JobLogger(true, true,false, true, true,true, paramsMap );
        try {
            jobLogger.LogMessage("this is a test", true, false, false);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
