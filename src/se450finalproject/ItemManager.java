package se450finalproject;

import java.util.HashMap;
import java.util.Map.Entry;

public class ItemManager {
	private static ItemManager instance;
	HashMap<String, Item> Items = new HashMap<>();
	
	private ItemManager(){	
		this.Items = ItemCatalogXML.getItemCatalogXML();
	}
	
	public static ItemManager getInstance(){
		if(instance == null)
		{
			instance = new ItemManager();
		}
		return instance;
	}
	
	public Item getItem(String itemId){
		return this.Items.get(itemId);	
	}
	
	public Boolean isValidItem(String itemId){
		//Item i = ItemManager.getInstance().getItem(itemId);
		if(this.Items.containsKey(itemId)){
			return true;
		}
		else {
			return false;
		}
	}
	
	public void printItemReport(){
		System.out.println("Item Catalog");
		for (Entry<String, Item> i: this.Items.entrySet()){
			String itemId = i.getKey();
			System.out.println(helper.PrintItemReport(itemId));
		}
	}
}
