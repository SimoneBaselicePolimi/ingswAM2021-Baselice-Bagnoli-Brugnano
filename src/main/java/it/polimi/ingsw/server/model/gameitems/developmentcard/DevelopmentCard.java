package it.polimi.ingsw.server.model.gameitems.developmentcard;

import it.polimi.ingsw.server.model.gameitems.MarbleColour;
import it.polimi.ingsw.server.model.gameitems.Production;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

import java.util.Map;

public class DevelopmentCard {
	private DevelopmentCardLevel level;
	private DevelopmentCardColour colour;
	private Production production;
	private int victoryPoints;
	private Map<ResourceType, Integer> purchaseCost;

	public DevelopmentCard (
			DevelopmentCardLevel level,
			DevelopmentCardColour colour,
			Production production,
			int victoryPoints,
			Map<ResourceType, Integer> purchaseCost){
		this.level=level;
		this.colour=colour;
		this.production=production;
		this.victoryPoints=victoryPoints;
		this.purchaseCost=purchaseCost;
	}

	public DevelopmentCardLevel getLevel() {
		return level;
	}

	public DevelopmentCardColour getColour() {
		return colour;
	}

	public Production getProduction() {
		return production;
	}

	public int getVictoryPoints() {
		return victoryPoints;
	}

	public  Map<ResourceType, Integer> getPurchaseCost(){
		return purchaseCost;
	}

}
