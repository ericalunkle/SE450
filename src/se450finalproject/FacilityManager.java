package se450finalproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import java.util.Set;

public class FacilityManager {
	 
	private static FacilityManager instance;
	private HashMap<String, FacilityImpl> Facilities = new HashMap<>();
	
	//Stores the facilities in a HashMap
	private FacilityManager(){
		this.Facilities =  LoadFacilityNetworkandInventoryXML.loadInventoryandNetwork();
	}
	
	public static FacilityManager getInstance(){
		if(instance == null)
		{
			instance = new FacilityManager();
		}
		return instance;
	}
	
	public Set<String> getNeighbors(FacilityImpl f){
		return f.getNeighbors();
	}
	
	public FacilityImpl getFacility(String facilityid){
		return this.Facilities.get(facilityid);
	}
	
	public Integer getFacilityCost(String facilityid){
		return this.Facilities.get(facilityid).getCost();
	}
	
	public Inventory getFacilityInventory(String facilityid){
		return this.Facilities.get(facilityid).getItems();
	}
	
	public ArrayList<FacilityImpl> findFacilityWithItemNeeded(String item){
		ArrayList<FacilityImpl> list = new ArrayList<>();
		for(FacilityImpl f: this.Facilities.values()){
			//System.out.println("Facility: " + f);
			for(String i: f.getItems().getFacilityItems().keySet()){
				//System.out.println("Item: " + i);
				if (i.equals(item) && (f.getItems().getFacilityItems().get(i) > 0)){
					//System.out.println("Item is getting added to the list");
					list.add(f);
				}
			}
		}
		return list;
	}
	
	public Schedule getFacilitySchedule(String facilityid){
		return this.Facilities.get(facilityid).getSchedule();
	}
	
	public void printReport(){
		for (Entry<String, FacilityImpl> f: this.Facilities.entrySet()){
			String facilityId = f.getKey();
			System.out.println(helper.PrintReport(facilityId));
		}
	}
	
	public void PrintShortestPath(){
		System.out.println(helper.PrintShortestPath("Santa Fe, NM", "Chicago, IL"));
	}
	
	
	public static void main(String[] args){
		FacilityImpl f = FacilityManager.getInstance().getFacility("Miami, FL");
		System.out.println("In facilityManager this is the destination facility: " + f);
	}
}
