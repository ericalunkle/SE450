package se450finalproject;

import java.util.Collections;
import java.util.Comparator;

public class FacilityRecord implements Comparable<FacilityRecord> {
	
	private String facilityName;
	private String itemName;
	private Integer quantity;
	private double processEndDay;
	private double travelTime;
	private double arrivalDay;
	
	public FacilityRecord(String facilityName, String itemName, Integer quantity, double processEndDay, double travelTime, double arrivalDay2) {
		this.facilityName = facilityName;
		this.itemName = itemName;
		this.quantity = quantity;
		this.processEndDay = processEndDay;
		this.travelTime = travelTime;
		this.arrivalDay = arrivalDay2;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public double getprocessEndDay() {
		return processEndDay;
	}

	public double getTravelTime() {
		return travelTime;
	}

	public double getArrivalDay() {
		return arrivalDay;
	}
	
	
	public String getItemName() {
		return itemName;
	}

	@Override
		public int compareTo(FacilityRecord fr2){
			int d1 = (int) this.arrivalDay;
			int d2 = (int) fr2.getArrivalDay();
			return Integer.compare(d1, d2);
	}
	
	
}
