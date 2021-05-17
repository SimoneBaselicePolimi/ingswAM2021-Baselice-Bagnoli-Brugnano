package it.polimi.ingsw.client.model;

import java.util.Set;

public class ProductionActionRepresentation extends GameActionRepresentation {
    private final PlayerRepresentation player;
    private final Set<ProductionRepresentation> productions;

    public ProductionActionRepresentation(PlayerRepresentation player, Set<ProductionRepresentation> productions) {
        this.player = player;
        this.productions = productions;
    }

    public PlayerRepresentation getPlayer() {
        return player;
    }

    public Set<ProductionRepresentation> getProductions() {
        return productions;
    }
}
