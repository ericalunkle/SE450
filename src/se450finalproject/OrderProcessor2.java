package se450finalproject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class OrderProcessor2 {
	
public static ArrayList<LogisticsRecord> ProcessOrder(String orderId){
		
		ArrayList <LogisticsRecord> logisticsSolution = new ArrayList<>();
	
		ArrayList<FacilityRecord> solutionList = new ArrayList<>();
		
		OrderDTO orderInfo = OrderManager.getInstance().getOrderDTO(orderId);
		
		for(String item: orderInfo.orderItemsList.keySet()){
				   
				if(ItemManager.getInstance().isValidItem(item)){
					
					while (orderInfo.orderItemsList.get(item) > 0) 
					{
						ArrayList<FacilityRecord> facilityRecords = new ArrayList<>();
						ArrayList<FacilityImpl> facilitiesContainingItem = new ArrayList<>();
						facilitiesContainingItem = FacilityManager.getInstance().findFacilityWithItemNeeded(item);
						
						if(facilitiesContainingItem.isEmpty()){
							System.out.println("Item: "+ item + "is on BackOrder.");
						}
						else{
						for(FacilityImpl facility: facilitiesContainingItem){
							if(!facility.equals(orderInfo.destination)){
							
								Integer currentFacilityQuantity = facility.getItems().getFacilityItems().get(item);
								Integer orderQuantity = orderInfo.orderItemsList.get(item);
								Integer quantityNeeded = Math.min(currentFacilityQuantity, orderQuantity);
								
								//Get the shortest path(in days)
								ArrayList<FacilityImpl> shortest = new ArrayList<>();
								
								FacilityImpl destinationFacility = orderInfo.destination;
								
								shortest = ShortestPath.findBestPath(facility, destinationFacility);
								int shortestSum = 0;
								for(int j = 0; j < shortest.size()-1; j++){
									shortestSum = shortestSum + shortest.get(j).getDistance(shortest.get(j+1).getFacilityId());
									}
								double shortestDays = (double) shortestSum/(8*50);
								double travelTime = shortestDays;
								
								//Number of days to process the quantity needed
								double daysToProcess = (double) quantityNeeded/facility.getRate();
								
								Schedule lastSchedule = facility.getSchedule();
								int lastDay = lastSchedule.getFacilitySchedule().size();
								
								double arrivalDay = Math.round(orderInfo.orderTime + travelTime + daysToProcess + lastDay);
								
								//generate a facility Record
								FacilityRecord fR = new FacilityRecord(facility.getFacilityId(), item, quantityNeeded, daysToProcess, travelTime, arrivalDay);
								//Add facility record to the list
								facilityRecords.add(fR);
							}
						}
						//No more facilities with the needed Item
												
						//Sort facilities with that item by arrival day
						Collections.sort(facilityRecords);
					
						//Process the Top Record
						FacilityRecord winningRecord = facilityRecords.get(0);
						FacilityImpl reductionFacility = FacilityManager.getInstance().getFacility(winningRecord.getFacilityName());
						Integer currentInventory = reductionFacility.getItems().getFacilityItems().get(item);
						reductionFacility.getItems().setItemQuantity(item, (currentInventory - winningRecord.getQuantity()));
						
						//Update the order Quantity
						Integer currentOrderQuantity = orderInfo.orderItemsList.get(item);
						OrderManager.getInstance().updateOrderQuantity(orderId, item, (currentOrderQuantity - winningRecord.getQuantity()));
						Integer updateOrderQuantity = OrderManager.getInstance().getOrderItemsList(orderId).get(item);
						//Update the schedule of the winning facility
						reductionFacility.getSchedule().blockDaysInSchedule(winningRecord.getprocessEndDay(), reductionFacility.getRate());
											
						solutionList.add(winningRecord);
									
					}
					//Order quantity is 0
					}
				}
				else{
					System.out.println("Item : " + item + " is not a valid item.");
				}
		}
		//No items left so move to next order;
			
		//Create logistics Record for the order
		for (FacilityRecord r: solutionList){
			String facilityId = r.getFacilityName();
			String itemId = r.getItemName();
			Integer quantity = r.getQuantity(); 
			double itemCost = quantity * ItemManager.getInstance().getItem(itemId).getPrice();
			double facilityProcessCost = FacilityManager.getInstance().getFacilityCost(facilityId) * r.getprocessEndDay();
			double transportCost = Math.ceil(r.getTravelTime()) * 500;
			double totalCost = itemCost + facilityProcessCost + transportCost;
			int arrivalDay = (int) r.getArrivalDay();
			LogisticsRecord lR = new LogisticsRecord(orderId, facilityId, itemId, quantity, totalCost, arrivalDay);
			logisticsSolution.add(lR);
		}
		
		return logisticsSolution;
	}


}
