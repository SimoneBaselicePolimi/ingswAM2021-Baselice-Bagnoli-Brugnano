package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.servermessage.ServerMessage;
import it.polimi.ingsw.localization.Localization;

import java.util.*;
import java.util.function.Function;

public class UserChoicesUtils {

    public static PossibleUserChoices makeUserChoose() {
        return new PossibleUserChoices();
    }

    public static class PossibleUserChoices {

        class UserChoice {
            final String description;
            final Runnable computation;


            UserChoice(String description, Runnable computation) {
                this.description = description;
                this.computation = computation;
            }

        }

        protected List<UserChoice> choices;

        PossibleUserChoices() {
            choices = new ArrayList<>();
        }


        public PossibleUserChoices addUserChoice(Runnable computation, String description) {
            choices.add(new UserChoice(description, computation));
            return this;
        }

        public PossibleUserChoices addUserChoiceLocalized(Runnable computation, String requestPlaceholder, Object... args) {
            return addUserChoice(computation, Localization.getLocalizationInstance().getString(requestPlaceholder, args));
        }

        public void apply() {
            //TODO
        }
    }
}
