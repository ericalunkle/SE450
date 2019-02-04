package se450finalproject;

import java.util.HashMap;

public class FacilityFactory {
	
	public static FacilityImpl createFacility (String facilityId, int cost, int rate, HashMap<String, Integer> connections, Schedule schedule) throws InvalidDataException
	{
		return new FacilityImpl(facilityId, cost, rate, connections, schedule);	
	}
	
	public static FacilityImpl createFacility(String facilityId, int cost, int rate, HashMap<String, Integer> connections, Schedule schedule, Inventory items) throws InvalidDataException
	{
		return new FacilityImpl(facilityId, cost, rate, connections, schedule, items);	
	}
}
