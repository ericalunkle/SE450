package se450finalproject;

public class LogisticsRecord {
	private double cost;
	private int arrivalDay;
	private String facilityId;
	private String itemId;
	private Integer quantity;
	private String orderId;
	
	
	public LogisticsRecord(String orderId2, String facilityId, String itemId, Integer quantity, double totalCost,
			int arrivalDay) {
		this.orderId = orderId2;
		this.cost = totalCost;
		this.arrivalDay = arrivalDay;
		this.facilityId = facilityId;
		this.itemId = itemId;
		this.quantity = quantity;
		
	}


	public int getArrivalDay() {
		return arrivalDay;
	}


	public String getFacilityId() {
		return facilityId;
	}


	public String getItemId() {
		return itemId;
	}


	public Integer getQuantity() {
		return quantity;
	}


	public double getCost() {
		return cost;
	}


	public String getOrderId() {
		return orderId;
	}


	@Override
	public String toString() {
		return "LogisticsRecord [cost=" + cost + ", orderId=" + orderId + ", arrivalDay=" + arrivalDay + ", facilityId="
				+ facilityId + ", itemId=" + itemId + ", quantity=" + quantity + "]";
	}



	
	
}
