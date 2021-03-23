package it.polimi.ingsw.model.gameitems;

import java.util.Map;

public interface ProductionDiscount {

	Map<ResourceType, Integer> getProductionDiscount();

	boolean isProductionDiscountActive();

}
