package it.polimi.ingsw.logger;

public class ProjectLogger {

    boolean logInConsole = true;

    private static ProjectLogger logger;

    public static ProjectLogger getLogger(){
        if (logger == null)
            logger = new ProjectLogger();
        return logger;
    }

    public void log(LogLevel logLevel, String message, Object... args){
        if(logInConsole)
            System.out.printf("[%s]: %s\n", logLevel, String.format(message, args));
    }

    public void log(Exception exception) {
        if(logInConsole)
            log(LogLevel.ERROR, "An exception has been thrown: \n%s\n%s", exception.getMessage(), exception.getCause());
    }

    public void setLogInConsole(boolean logInConsole) {
        this.logInConsole = logInConsole;
    }

}
