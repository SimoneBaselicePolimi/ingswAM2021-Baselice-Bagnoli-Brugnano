package it.polimi.ingsw.model.gameitems;

public interface ProductionDiscount {

	public abstract Map<ResourceType,int> getProductionDiscount();

	public abstract boolean isProductionDiscountActive();

}
