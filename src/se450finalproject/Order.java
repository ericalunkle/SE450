package se450finalproject;

import java.util.HashMap;

public interface Order {
	
	public String getOrderId();
	public void setOrderId(String orderId);
	public FacilityImpl getDestination();
	public Integer getOrderTime();
	public HashMap<String, Integer> getOrderItemsList();
}
