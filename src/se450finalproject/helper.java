package se450finalproject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

public class helper {
	
	public static String PrintReport (String facilityId){
		FacilityImpl f = FacilityManager.getInstance().getFacility(facilityId);
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0;i<82;i++){
            stringBuilder.append('-');
        }
        stringBuilder.append("\n");
        //print location
        stringBuilder.append(f.getFacilityId());
        stringBuilder.append("\n");
        for(int i = 0;i<12;i++){
            stringBuilder.append('-');
        }
        stringBuilder.append("\n");
        stringBuilder.append("Rate per Day: ");
        stringBuilder.append(f.getRate());
        stringBuilder.append("\n");
        stringBuilder.append("Cost per Day: $");
        stringBuilder.append(f.getCost());
        stringBuilder.append("\n");
        stringBuilder.append("\n");
        stringBuilder.append("Direct Links:");
        stringBuilder.append("\n");
      //loop through the facilities by going through the keys
        for(Entry<String, Integer> pair: f.getConnections().entrySet()){
        	stringBuilder.append(pair.getKey());
        	stringBuilder.append(" ");
            stringBuilder.append("(");
            double pair1 = (pair.getValue());
            stringBuilder.append(pair1/(8*50));
            stringBuilder.append("d); ");
        }
        stringBuilder.append("\n");
        stringBuilder.append("\n");
        stringBuilder.append("Active Inventory:");
        stringBuilder.append("\n");
        stringBuilder.append(String.format("\t %8s \t %-8s", "Item ID", "Quantity"));
        stringBuilder.append("\n");
        Inventory inventory = f.getItems();
        HashMap<String, Integer> items = inventory.getFacilityItems();
        for(Entry<String, Integer> pair: items.entrySet()){
        	stringBuilder.append("\t");
            stringBuilder.append(String.format("%8s",pair.getKey()));
            stringBuilder.append("\t ");
            stringBuilder.append(String.format("%-8s",pair.getValue()));
            stringBuilder.append("\n");
        }
        stringBuilder.append("Schedule:");
        stringBuilder.append("\n");
        stringBuilder.append(String.format("%-11s","Day: "));
        Schedule s = f.getSchedule();
       
        HashMap<Integer, Integer> sMap = s.getFacilitySchedule();
        if(sMap.isEmpty()){
        	for(int i = 1; i<=20; i++){
            	String day = Integer.toString(i).format("%-3s", i);
            	stringBuilder.append(day);
            }
            stringBuilder.append("\n");
            stringBuilder.append(String.format("%-11s","Available: "));
            for(int i = 1; i<=20; i++){
            	String rate = Integer.toString(f.getRate()).format("%-3s", f.getRate());
            	stringBuilder.append(rate);
            }
        }
        else{
	        for(Entry<Integer, Integer> pair: sMap.entrySet()){
	        	String day = Integer.toString(pair.getKey()).format("%-3s", pair.getKey());
	        	stringBuilder.append(day);
	        }
	     
	        stringBuilder.append("\n");
	        stringBuilder.append(String.format("%-11s","Available: "));
	        for(Entry<Integer, Integer> pair: sMap.entrySet()){
	        	String day = Integer.toString(pair.getValue()).format("%-3s", pair.getValue());
	        	stringBuilder.append(day);
	        }
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
	}
	
	
	public static String PrintItemReport(String itemId){
		Item i = ItemManager.getInstance().getItem(itemId);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(String.format("%-8s", i.getItemId()));
		stringBuilder.append(" : $");
		stringBuilder.append(i.getPrice());
		stringBuilder.append("\n");
		return stringBuilder.toString();
	}
	
	public static String PrintShortestPath(String facility1, String facility2){
		FacilityImpl f1 = FacilityManager.getInstance().getFacility(facility1);
		FacilityImpl f2 = FacilityManager.getInstance().getFacility(facility2);
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(facility1 + " to " + facility2);
		stringBuilder.append("\n");
		stringBuilder.append("\u2022 ");
		ArrayList<FacilityImpl> shortest = new ArrayList<>();
		shortest = ShortestPath.findBestPath(f1, f2);
		int shortestSum = 0;
		for(int i = 0; i < shortest.size()-1; i++){
			shortestSum = shortestSum + shortest.get(i).getDistance(shortest.get(i+1).getFacilityId());
		}
		for(int i = 0; i < shortest.size(); i++){
			stringBuilder.append(shortest.get(i).getFacilityId());
			stringBuilder.append(" --> ");
		}
		stringBuilder.append(" = " );
		stringBuilder.append(shortestSum + " mi");
		stringBuilder.append("\n");
		double days = (double) shortestSum/(8*50);
		String output = String.format("\u2022 %d mi / (8 hours per day * 50 mph) = %.2f days" , shortestSum, days);
		stringBuilder.append(output);
		
		return stringBuilder.toString();
	}
	
	public static String PrintOrderSolution(String orderId){
		StringBuilder stringBuilder = new StringBuilder();
		OrderDTO o = OrderManager.getInstance().getOrderDTO(orderId);
		
		
		for(int i = 0;i<82;i++){
	            stringBuilder.append('-');
	        }
		stringBuilder.append("\n");
		stringBuilder.append("Order Id: " + orderId);
		stringBuilder.append("\n");
		stringBuilder.append("Order Time: Day " + o.orderTime);
		stringBuilder.append("\n");
		stringBuilder.append("Destination: " + o.destination.getFacilityId());
		stringBuilder.append("\n");
		stringBuilder.append("\n");
		stringBuilder.append("List of Order Items:");
		stringBuilder.append("\n");
		for(Entry<String, Integer> pair: o.orderItemsList.entrySet()){
			stringBuilder.append("Item ID:   " + pair.getKey() + ",    Quantity:  " + pair.getValue());
			stringBuilder.append("\n");
		}
		
		ArrayList<LogisticsRecord> solution = OrderProcessor2.ProcessOrder(orderId);
		stringBuilder.append("\n");
		stringBuilder.append("Processing Solution:");
		stringBuilder.append("\n");
		stringBuilder.append("\n");
		
		double totalCost = 0;
		ArrayList<Integer> days = new ArrayList<>();
		for(Entry<String, Integer> pair: o.orderItemsList.entrySet()){
			int totalQuantity = 0;
			double totalItemCost = 0;
			int sources = 0;
			for( LogisticsRecord f: solution){
				if (f.getItemId().equals(pair.getKey())){
		            totalQuantity = totalQuantity + f.getQuantity();		    
		            int cost = (int) f.getCost();
		            totalItemCost = totalItemCost + f.getCost();
		            days.add(f.getArrivalDay());
		            sources = sources + 1;
				}
			}
	       totalCost = totalCost + totalItemCost;

		}
		stringBuilder.append("Total Cost: $");
		NumberFormat numberFormat = new DecimalFormat("#,###.00");
		stringBuilder.append(String.format("%8s", numberFormat.format(totalCost)));
		Collections.sort(days);
	    stringBuilder.append("\n ");
		stringBuilder.append("1st delivery day: " + days.get(0));
	    stringBuilder.append("\n ");
		stringBuilder.append("Last deliver day: " + days.get(days.size() -1));
	    stringBuilder.append("\n ");
	    stringBuilder.append("Order Items:");
	    stringBuilder.append("\n ");
		stringBuilder.append(String.format("%-8s \t %-10s \t %-8s \t %-14s \t %-10s \t %-10s", "Item ID", "Quantity", "Cost", "# Sources Used", "First Day", "Last Day"));
		stringBuilder.append("\n");
	    
	    for(Entry<String, Integer> pair: o.orderItemsList.entrySet()){
	    	stringBuilder.append(String.format("%-8s", pair.getKey()));
	    	int totalQuantity = 0;
			double totalItemCost = 0;
			int sources = 0;
			ArrayList<Integer> itemDays = new ArrayList<>();
			for( LogisticsRecord f: solution){
				if (f.getItemId().equals(pair.getKey())){					
		            totalQuantity = totalQuantity + f.getQuantity();		            
		            double cost = f.getCost();		      
		            totalItemCost = totalItemCost + f.getCost();
		            itemDays.add(f.getArrivalDay());
		            sources = sources + 1;
				}
			}
			Collections.sort(itemDays);
			stringBuilder.append("\t ");
	    	stringBuilder.append(String.format("%-10s", totalQuantity));
			stringBuilder.append("\t ");
			stringBuilder.append("$");
			stringBuilder.append(String.format("%8s", numberFormat.format(totalItemCost)));
			stringBuilder.append("\t ");
			stringBuilder.append(String.format("%-14s ", sources));
			stringBuilder.append("\t ");
			stringBuilder.append(String.format("%-10s", itemDays.get(0)));
			stringBuilder.append("\t ");
			stringBuilder.append(String.format("%-10s", itemDays.get(itemDays.size()-1)));
			stringBuilder.append("\n");

	    }	    	    	    	   
	    
		return stringBuilder.toString();		
		
	}
	
	public static String PrintDetailedOrderSolution(String orderId){
		StringBuilder stringBuilder = new StringBuilder();
		OrderDTO o = OrderManager.getInstance().getOrderDTO(orderId);
		
		for(int i = 0;i<82;i++){
            stringBuilder.append('-');
        }
		stringBuilder.append("\n");
		stringBuilder.append("Order: " + orderId);
		stringBuilder.append("\n");
		stringBuilder.append("Order Time: Day " + o.orderTime);
		stringBuilder.append("\n");
		stringBuilder.append("Destination: " + o.destination.getFacilityId());
		stringBuilder.append("\n");
		stringBuilder.append("\n");
		stringBuilder.append("List of Order Items:");
		stringBuilder.append("\n");
		
		for(Entry<String, Integer> pair: o.orderItemsList.entrySet()){
			stringBuilder.append("\t");
			stringBuilder.append("Item ID:   " + pair.getKey() + ",    Quantity:  " + pair.getValue());
			stringBuilder.append("\n");
		}
		
		ArrayList<LogisticsRecord> solution = OrderProcessor2.ProcessOrder(orderId);
		stringBuilder.append("\n");
		stringBuilder.append("Processing Solution:");
		stringBuilder.append("\n");
		stringBuilder.append("\n");
		
		double totalCost = 0;
		for(Entry<String, Integer> pair: o.orderItemsList.entrySet()){
			stringBuilder.append(pair.getKey() + ":");
			stringBuilder.append("\n");
            stringBuilder.append("\t ");

			int totalQuantity = 0;
			double totalItemCost = 0;
			int sources = 0;
			ArrayList<Integer> days = new ArrayList<>();
			stringBuilder.append(String.format("%-20s \t %-8s  \t %-8s  \t %-8s", "Facility", "Quantity", "Cost", "Arrival Day"));
			stringBuilder.append("\n");
			for( LogisticsRecord f: solution){
				if (f.getItemId().equals(pair.getKey())){
					//stringBuilder.append("\n");
					//stringBuilder.append("\t ");
		            stringBuilder.append("\t ");
					stringBuilder.append(String.format("%-20s", f.getFacilityId()));
		            stringBuilder.append("\t ");
		            stringBuilder.append(String.format("%-8s",f.getQuantity()));
		            totalQuantity = totalQuantity + f.getQuantity();
		            stringBuilder.append("\t ");
		           // int cost = (int) f.getCost();
					NumberFormat numberFormat = new DecimalFormat("#,###.00");
		            stringBuilder.append(String.format("%-8s", "$" + numberFormat.format(f.getCost())));
		            stringBuilder.append("\t ");
		            totalItemCost = totalItemCost + f.getCost();
		            stringBuilder.append(String.format("%-8s",f.getArrivalDay()));
		            stringBuilder.append("\n ");
		            days.add(f.getArrivalDay());
		            sources = sources + 1;
				}
				
			}
				stringBuilder.append("\t ");
				stringBuilder.append(String.format("%-20s", "TOTAL"));
				stringBuilder.append("\t ");
	            stringBuilder.append(String.format("%-8s",totalQuantity));
				stringBuilder.append("\t ");
				NumberFormat numberFormat = new DecimalFormat("#,###.00");
				stringBuilder.append(String.format("%-8s", "$" + numberFormat.format(totalItemCost)));
	            stringBuilder.append("\t ");
				Collections.sort(days);
				stringBuilder.append(String.format("%-8s", "[" + days.get(0) + " - "));	    
				stringBuilder.append(days.get(days.size() -1) + "]");
	            stringBuilder.append("\n ");
	            stringBuilder.append("\n ");
	            totalCost = totalCost + totalItemCost;

		}
		
        stringBuilder.append("\n ");
        stringBuilder.append("Total Cost:");
		NumberFormat numberFormat = new DecimalFormat("#,###.00");
        stringBuilder.append(String.format("%20s", "$" + numberFormat.format(totalCost)));
		return stringBuilder.toString();		
		
	}
}
	/*public static void main(String[] args) {
		FacilityManager.getInstance();
		ItemManager.getInstance();
		OrderManager.getInstance();
		System.out.println(helper.PrintDetailedOrderSolution("TO-001"));
		System.out.println(helper.PrintDetailedOrderSolution("TO-002"));
		System.out.println(helper.PrintDetailedOrderSolution("TO-003"));
		System.out.println(helper.PrintDetailedOrderSolution("TO-004"));
		System.out.println(helper.PrintDetailedOrderSolution("TO-005"));
		System.out.println(helper.PrintDetailedOrderSolution("TO-006"));
	}*/
