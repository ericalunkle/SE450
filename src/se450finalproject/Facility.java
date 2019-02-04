package se450finalproject;

public interface Facility {
	public String getFacilityId();
	public void setFacilityId(String facilityId);
	public void setInventory(Inventory inventory);
	public Inventory getItems();
	public Schedule getSchedule();
	public Object getNeighbors();
	public int getDistance(String facilityId);
	
}
