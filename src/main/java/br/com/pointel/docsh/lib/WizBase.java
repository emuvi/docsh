package br.com.pointel.docsh.lib;

/**
 *
 * @author emuvi
 */
public class WizBase {
    
    public static final String APP_NAME = "docsh";
    
    public static void sleep(long millis) {
        try {
            Thread.currentThread().sleep(millis);
        } catch(Exception e) {}
    }
    
    public static Thread startDaemon(Runnable task, String name) {
        var result = new Thread(task, name);
        result.setDaemon(true);
        result.start();
        return result;
    }
    
}
