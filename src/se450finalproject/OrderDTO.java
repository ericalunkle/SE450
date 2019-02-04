package se450finalproject;

import java.util.HashMap;

public class OrderDTO {
	public String orderId;
	public FacilityImpl destination;
	public Integer orderTime;
	public HashMap<String, Integer> orderItemsList;
	
	public OrderDTO(String orderIdin, FacilityImpl destinationin, Integer orderTimein, HashMap<String, Integer> orderItemListin){
		orderId = orderIdin;
		destination = destinationin;
		orderTime = orderTimein;
		orderItemsList = orderItemListin;
	}

	@Override
	public String toString() {
		return "OrderDTO [orderId=" + orderId + ", destination=" + destination + ", orderTime=" + orderTime
				+ ", orderItemsList=" + orderItemsList + "]";
	}
	
	
}
