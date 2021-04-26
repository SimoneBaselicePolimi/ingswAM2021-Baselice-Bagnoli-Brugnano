package it.polimi.ingsw.logger;

public class ProjectLogger {

    private static ProjectLogger logger;

    public static ProjectLogger getLogger(){
        if (logger == null)
            logger = new ProjectLogger();
        return logger;
    }

    public void log(LogLevel logLevel, String message, Object... args){
        System.out.printf("[%s]: %s\n", logLevel, String.format(message, args));
        //TODO
    }
}
