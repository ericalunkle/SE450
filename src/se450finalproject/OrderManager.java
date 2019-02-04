package se450finalproject;

import java.util.HashMap;
import java.util.Map.Entry;

public class OrderManager {
	private static OrderManager instance;
	
	private HashMap<String, Order> orders = new HashMap<>();
	
	private OrderManager(){
		this.orders =  OrderXML.loadOrder();
	}
	
	public static OrderManager getInstance(){
		if(instance == null)
		{
			instance = new OrderManager();
		}
		return instance;
	}
	
	
	public Order getOrderId(String orderId){
		return this.orders.get(orderId);
	}
	
	public OrderDTO getOrderDTO(String i){
		Order o = orders.get(i);
		return new OrderDTO(o.getOrderId(), o.getDestination(), o.getOrderTime(), o.getOrderItemsList());
	}
	
	public HashMap<String, Order> getOrders(){
		return this.orders;
	}
	
	public FacilityImpl getOrderDestination(String orderId){
		return this.orders.get(orderId).getDestination();
	}
	
	public Integer getOrderStartDay(String orderId){
		return this.orders.get(orderId).getOrderTime();
	}
	
	public HashMap<String, Integer> getOrderItemsList(String orderId){
		return this.orders.get(orderId).getOrderItemsList();
	}
	
	public void updateOrderQuantity(String orderId, String itemId, Integer newQuantity){
		getOrderItemsList(orderId).replace(itemId, newQuantity);
	}
	
	
	public void printReport(){
		for(int i = 1; i <= this.orders.size(); i++){
			String orderId = "TO-00" + i;
			System.out.println(helper.PrintDetailedOrderSolution(orderId));
		}
	}
	
	/*public static void main(String[] args){
		FacilityImpl destination = OrderManager.getInstance().getOrderDestination("TO-001");
		System.out.println(destination);
	}*/
}
