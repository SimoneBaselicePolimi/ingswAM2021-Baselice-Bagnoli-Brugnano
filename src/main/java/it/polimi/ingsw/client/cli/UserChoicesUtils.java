package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.ClientManager;
import it.polimi.ingsw.localization.Localization;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class UserChoicesUtils {

    public static PossibleUserChoices makeUserChoose(CliClientManager clientManager) {
        return new PossibleUserChoices(clientManager);
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

        protected final CliClientManager clientManager;
        protected List<UserChoice> choices;

        PossibleUserChoices(CliClientManager clientManager) {
            this.clientManager = clientManager;
            choices = new ArrayList<>();
        }


        public PossibleUserChoices addUserChoice(Runnable computation, String description) {
            choices.add(new UserChoice(description, computation));
            return this;
        }

        public PossibleUserChoices addUserChoiceLocalized(Runnable computation, String requestPlaceholder, Object... args) {
            return addUserChoice(computation, Localization.getLocalizationInstance().getString(requestPlaceholder, args));
        }

        public CompletableFuture<Void> apply() {
            clientManager.tellUserLocalized("client.cli.game.possibleUserChoices");
            for(int i = 0; i < choices.size(); i++)
                clientManager.tellUser(String.format("%d) %s", i+1, choices.get(i).description));
            return clientManager.askUserLocalized("client.cli.game.makeChoice")
                .thenCompose(input -> {
                    int num = Integer.parseInt(input);
                    if(num < 1 || num > choices.size()) {
                        clientManager.tellUserLocalized("client.cli.game.invalidChoice");
                        return apply();
                    } else {
                        clientManager.tellUser("");
                        choices.get(num-1).computation.run();
                        return CompletableFuture.completedFuture(null);
                    }
                });
        }

    }
}
