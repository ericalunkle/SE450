package se450finalproject;

import java.util.HashMap;

public class Inventory {
	
	private HashMap<String, Integer> facilityItems;
	
	public Inventory (HashMap<String, Integer> afacilityItems) throws InvalidDataException{
		setFacilityItems(afacilityItems);
	}

	public Inventory() {
	}

	public void setFacilityItems(HashMap<String, Integer> afacilityItems) throws InvalidDataException{
		for(String itemId: afacilityItems.keySet()){
			if(!ItemManager.getInstance().isValidItem(itemId)){
				throw new InvalidDataException("Item " + itemId + " is not in Item Catalog");
			}
		}
		this.facilityItems = afacilityItems;
	}
	

	public HashMap<String, Integer> getFacilityItems() {
		return facilityItems;
	}

	public void setItemQuantity(String itemId, Integer newQuantity){
		this.facilityItems.replace(itemId, newQuantity);
	}
	
	@Override
	public String toString() {
		return "Inventory [facilityItems=" + facilityItems + "]";
	}
	
	
	
	
}
