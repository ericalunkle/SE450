package se450finalproject;

import java.util.HashMap;

public class Schedule {

	private HashMap<Integer, Integer> facilitySchedule;
	
	public Schedule (HashMap<Integer, Integer> schedule){
		setFacilitySchedule(schedule);
	}

	private void setFacilitySchedule(HashMap<Integer, Integer> afacilitySchedule) {
		this.facilitySchedule = afacilitySchedule;
	}
	
	public HashMap<Integer, Integer> getFacilitySchedule() {
		return facilitySchedule;
	}
	
	public void blockDaysInSchedule(double numDays, int rate){
		//System.out.println("Starting to block out days");
		int i = 1;
		
		int totalQuantity = (int) Math.ceil(numDays*rate);
		
		if(totalQuantity > 0){
			for(int j = 0; j < this.getFacilitySchedule().size(); j++){
				if(this.getFacilitySchedule().containsKey(i)){
					if(this.getFacilitySchedule().get(i) > 0){
						totalQuantity = totalQuantity - this.getFacilitySchedule().get(i);
						this.getFacilitySchedule().put(i, 0);
					}
					i++;
				}
			}
			
			for(int day = i; day <= numDays + i ; day++){
				if(totalQuantity <= 0){
					break;
				}
				else {
					if(day == Math.ceil(numDays)){
						double x = day -numDays;
						this.getFacilitySchedule().put(day, (int) (Math.round(rate*x)));
					}
					else {
						this.getFacilitySchedule().put(day, 0);
					}
					
					totalQuantity = totalQuantity - rate;	
					}
			}
		}
	}

	@Override
	public String toString() {
		return "Schedule [facilitySchedule=" + facilitySchedule + "]";
	}
	
}
