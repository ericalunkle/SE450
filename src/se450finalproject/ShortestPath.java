package se450finalproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ShortestPath {
	public static HashMap<String, Integer> pairs = new HashMap<>();
	public static HashSet<String> seen = new HashSet<>();
	public static ArrayList<FacilityImpl> lowPath = new ArrayList<>();
	
	public static ArrayList<FacilityImpl> findBestPath(FacilityImpl start, FacilityImpl end){
		lowPath.clear();
		mapPairs(start);
		ArrayList<FacilityImpl> pathList = new ArrayList<>();
		pathList.add(start);
		findPaths(start, end, pathList);
		return lowPath;
	}

	public static void mapPairs(FacilityImpl init){
		seen.add(init.getFacilityId());
		for(String neighbor: init.getNeighbors()){
			int distancebetween = FacilityManager.getInstance().getFacility(neighbor).getDistance(init.getFacilityId());
			String pairString = init.getFacilityId() + ":" + neighbor;
			pairs.put(pairString, distancebetween);
			if(!seen.contains(neighbor)){
				mapPairs(FacilityManager.getInstance().getFacility(neighbor));
			}
		}
		return;
	}

	public static void findPaths(FacilityImpl start, FacilityImpl end, ArrayList<FacilityImpl> pathList){
		//System.out.println("The start facility is: " + start.getFacilityId() + " The end facility is" + end.getFacilityId());
		if(start.getFacilityId().equals(end.getFacilityId())){
			if(lowPath.isEmpty()){
				lowPath = pathList;
				return;
			}
			
			else{
				int pathListSum = 0;
				for(int i = 0; i < pathList.size()-1; i++){
					pathListSum = pathListSum + pathList.get(i).getDistance(pathList.get(i+1).getFacilityId());
				}
				int lowPathSum = 0;
				for(int i = 0; i < lowPath.size()-1; i++){
					lowPathSum = lowPathSum + lowPath.get(i).getDistance(lowPath.get(i+1).getFacilityId());
				}
				if(pathListSum < lowPathSum){
					lowPath = pathList;
					return;
					}
				else {
					return; }
			}
		}
		else {
			HashSet<String> fromHere = new HashSet<>();
			for(String pair : pairs.keySet()){
				String[] parts = pair.split(":");
				String parts1 = parts[0];
				String parts2 = parts[1];
				if(parts1.equals(start.getFacilityId())){
					fromHere.add(pair); //Adds "FacilityId1:FacilityId2"
				}
			}
			

			for(String pair: fromHere){
				String[] parts = pair.split(":");
				String parts1 = parts[0];
				String parts2 = parts[1];
				if(!pathList.contains(FacilityManager.getInstance().getFacility(parts2))){
					ArrayList<FacilityImpl> newPath = new ArrayList<>(pathList);
					newPath.add(FacilityManager.getInstance().getFacility(parts2));
					findPaths(FacilityManager.getInstance().getFacility(parts2), end, newPath);
				}
			}
			return;
		} 
	}

}
