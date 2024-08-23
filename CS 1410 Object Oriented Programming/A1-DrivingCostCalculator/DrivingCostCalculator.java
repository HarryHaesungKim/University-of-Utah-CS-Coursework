/*
 * CS1410 Assignment 1: Driving Cost Calculator
 * Harry Kim 1/12/19
 */

package a1;

public class DrivingCostCalculator {
	public static void main(String[] args) {
		double milesDriven = 100.0;
		
		double milesPerGallon = 10.0;
		double dollarsPerGallon = 2.0;
		
		double milesPerKilowatt = 4.0;
		double dollarsPerKilowatt = 0.106;
		
		double G = calculateGasTripCost(milesDriven, milesPerGallon, dollarsPerGallon);
		double K = calculateElectricTripCost(milesDriven, milesPerKilowatt, dollarsPerKilowatt);
		
		String formattedNumberG = String.format("%.2f", G);
		String formattedNumberK = String.format("%.2f", K);
		
		displayBanner();
		System.out.println("The cost of a " + milesDriven + " mile trip by gas is $" + formattedNumberG + " and by electric is $" + formattedNumberK);
			
	}
	//Displays the banner.
	public static void displayBanner() {
		System.out.println("****************************");
		System.out.println();
		System.out.println("* Drivning Cost Calculator *");
		System.out.println();
		System.out.println("****************************");
		System.out.println();
	}
	//Calculates cost of using a gas car.
	public static double calculateGasTripCost(double milesToDrive, double milesPerGallon, double dollarsPerGallon) {
		double gallons = milesToDrive / milesPerGallon;
		double finalCost = gallons * dollarsPerGallon;
		return finalCost;
		
	}
	//Calculates cost of using an electric car.
	public static double calculateElectricTripCost(double milesToDrive, double milesPerKWh, double dollarsPerKWh) {
		double kilowatts = milesToDrive / milesPerKWh;
		double finalCost = kilowatts * dollarsPerKWh;
		return finalCost;
		
	}
	

}
