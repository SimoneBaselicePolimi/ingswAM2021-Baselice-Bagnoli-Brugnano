package it.polimi.ingsw.localization;

import it.polimi.ingsw.server.model.FileManager;

import java.util.Map;

public class Localization {

    private static Localization localization;
    private static Map<String, Object> langLocalization;
    //startMessage: game started
    //setup:
    //  leaderCardMessage: the player...
    //mainAction:
    //   playerTurnStarted: the turn of player %s has started
    //Localization.getLocalization.getString("startMessage");
    //Localization.getLocalization.getString("setup.leaderCardMessage");
    //Localization.getLocalization.getString("mainAction.playerTurnStarted", player1);


    private Localization(FileManager fileManager){
    }

    public static Localization getLocalization(){
        return localization;
    }

    public String getString(String placeholder, Object... args) throws IllegalArgumentException{
        String[] tokens = placeholder.split(".");
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
