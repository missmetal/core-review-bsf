

public class Main {

    public static void main (String args[]){

        try {
            JobLogger.LogMessage("this is a test", true, false, false);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
