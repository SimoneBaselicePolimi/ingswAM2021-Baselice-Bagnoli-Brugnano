package it.polimi.ingsw.localization;

import it.polimi.ingsw.utils.FileManager;

import java.io.IOException;
import java.util.Map;

public class Localization {

    public static final String DEFAULT_LANG = "en";
    private static Localization localizationInstance;

    private final FileManager fileManager;
    private Map<String, Object> langLocalization;

    private Localization(FileManager fileManager){
        this.fileManager = fileManager;
        try {
            langLocalization = fileManager.getLocalization(DEFAULT_LANG);
        } catch (IOException e) {
            //TODO
            e.printStackTrace();
        }
    }

    public static Localization getLocalizationInstance(){
        if(localizationInstance == null)
            localizationInstance = new Localization(FileManager.getFileManagerInstance());
        return localizationInstance;
    }


    public void setLocalizationLanguage(String localizationLanguage) {
        try {
            langLocalization = fileManager.getLocalization(localizationLanguage);
        } catch (IOException e) {
            //TODO
            e.printStackTrace();
        }
    }

    public String getString(String placeholder, Object... args) throws IllegalArgumentException{
        String[] tokens = placeholder.split("\\.");
        Object o = langLocalization;
        for (int i = 0; i < tokens.length; i++){
            if (o instanceof Map && ((Map<String, Object>) o).containsKey(tokens[i]))
                o = ((Map<String, Object>) o).get(tokens[i]);
            else
                throw new IllegalArgumentException("There is no valid localization associated to this placeholder");
        }
        return String.format((String)o, args);
    }

}
