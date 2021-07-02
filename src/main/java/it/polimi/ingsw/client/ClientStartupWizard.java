package it.polimi.ingsw.client;

import it.polimi.ingsw.localization.Localization;

public class ClientStartupWizard {

    public static void main(String[] args) {
        System.out.println(Localization.getLocalizationInstance().getString("client.startupWizard"));
    }

}
