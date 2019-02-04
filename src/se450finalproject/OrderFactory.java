package se450finalproject;

import java.util.HashMap;

public class OrderFactory {
	
	public static Order createOrder (String orderId, FacilityImpl destination, Integer orderTime, HashMap<String, Integer> orderItemsList) throws InvalidDataException
	{
		return new OrderImpl(orderId, destination, orderTime, orderItemsList);
	}

}
