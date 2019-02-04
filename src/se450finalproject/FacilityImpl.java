package se450finalproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class FacilityImpl implements Facility{
	private String facilityId;
	private int cost;
	private int rate;
	private Inventory items;
	private HashMap<String, Integer> connections;
	private Schedule schedule;
	
	public FacilityImpl(){
	}

	public FacilityImpl(String facilityId2, int acost, int arate, HashMap<String, Integer> aconnections, Schedule aschedule) throws InvalidDataException {
		setFacilityId(facilityId2);
		setCost(acost);
		setRate(arate);
		setConnections(aconnections);
		setSchedule(aschedule);
	}
	
	public FacilityImpl(String facilityId2, int acost, int arate, HashMap<String, Integer> aconnections, Schedule aschedule, Inventory aitems) throws InvalidDataException {
		setFacilityId(facilityId2);
		setCost(acost);
		setRate(arate);
		setConnections(aconnections);
		setInventory(aitems);
		setSchedule(aschedule);
	}

	public int getCost() {
		return cost;
	}

	
	public void setCost(int cost) throws InvalidDataException{
		if(cost < 0){
			throw new InvalidDataException("Cost is less than 0");
		}
		this.cost = cost;
		
	}

	public void setRate(int rate) throws InvalidDataException{
		if(rate < 0){
			throw new InvalidDataException("Rate is negative");
		}
		this.rate = rate;
	}

	public int getRate() {
		return rate;
	}

	public HashMap<String, Integer> getConnections() {
		return connections;
	}

	public void setConnections(HashMap<String, Integer> connections) {
		this.connections = connections;
	}

	public String getFacilityId() {
		return this.facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	

	public Inventory getItems() {
		return this.items;
	}
	
	public void setInventory(Inventory list){
		this.items = list;
	}
	
	public Set<String> getNeighbors(){
		return this.getConnections().keySet();
	}

	public int getDistance(String facilityId2){
		return this.getConnections().get(facilityId2);
	}
	
	
	public Schedule getSchedule() {
		return this.schedule;
	}
	
	public void setSchedule(Schedule aschedule){
		this.schedule = aschedule;
	}
	
	
	@Override
	public String toString() {
		return "Facility [facilityId=" + facilityId + ", cost=" + cost + ", rate=" + rate + ", items=" + items
				+ ", connections=" + connections + "] \n";
	}


	
	
	
}
