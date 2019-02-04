package se450finalproject;

public class Main {

	public static void main(String[] args) {
		ItemManager.getInstance();
		FacilityManager.getInstance().printReport();
		OrderManager.getInstance().printReport();
		FacilityManager.getInstance().printReport();
		
		/*System.out.println(helper.PrintShortestPath("Santa Fe, NM", "Chicago, IL"));
		System.out.println("\n" + helper.PrintShortestPath("Atlanta, GA", "St. Louis, MO"));
		System.out.println("\n" + helper.PrintShortestPath("Seattle, WA", "Nashville, TN"));
		System.out.println("\n" + helper.PrintShortestPath("New York City, NY", "Phoenix, AZ"));
		System.out.println("\n" + helper.PrintShortestPath("Fargo, ND", "Austin, TX"));
		System.out.println("\n" + helper.PrintShortestPath("Denver, CO", "Miami, FL"));
		System.out.println("\n" + helper.PrintShortestPath("Austin, TX", "Norfolk, VA"));
		System.out.println("\n" + helper.PrintShortestPath("Miami, FL", "Seattle, WA"));
		System.out.println("\n" + helper.PrintShortestPath("Los Angeles, CA", "Chicago, IL"));
		System.out.println("\n" + helper.PrintShortestPath("Detroit, MI", "Nashville, TN"));
		*/
	}

}
