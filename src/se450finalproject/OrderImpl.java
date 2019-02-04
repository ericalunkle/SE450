package se450finalproject;

import java.util.HashMap;

public class OrderImpl implements Order{
	private String orderId;
	private FacilityImpl destination;
	private Integer orderTime;
	private HashMap<String, Integer> orderItemsList;
	
	
	public OrderImpl(String orderId, FacilityImpl destination, Integer orderTime, HashMap<String, Integer> orderItemsList) throws InvalidDataException {
		setOrderId(orderId);
		setDestination(destination);
		setOrderTime(orderTime);
		setOrderItemsList(orderItemsList);
	}

	private void setOrderItemsList(HashMap<String, Integer> orderItemsList2) {
		this.orderItemsList = orderItemsList2;
	}
	
	private void setOrderTime(Integer orderTime2) throws InvalidDataException{
		if(orderTime2 < 0){
			throw new InvalidDataException("Order Day is negative");
		}
		this.orderTime = orderTime2;
	}

	private void setDestination(FacilityImpl destination2) {
		this.destination = destination2;
	}

	@Override
	public String getOrderId() {
		return this.orderId;
	}

	@Override
	public void setOrderId(String orderId2) {
		this.orderId = orderId2;
	}

	public FacilityImpl getDestination() {
		return destination;
	}

	public Integer getOrderTime() {
		return orderTime;
	}

	public HashMap<String, Integer> getOrderItemsList() {
		return orderItemsList;
	}

	@Override
	public String toString() {
		return "OrderImpl [orderId=" + orderId + ", destination=" + destination + ", orderTime=" + orderTime
				+ ", orderItemsList=" + orderItemsList + "]";
	}
	
}
