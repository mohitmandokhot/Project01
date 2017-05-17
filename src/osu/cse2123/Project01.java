/**
 * 
 */
package osu.cse2123;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author mandokhot.1
 *
 */
public class Project01 {

	/**
	 * Generate an inventory report. Read data about store's inventory saved in
	 * a .txt file Analyze information about the stock held 
	 * Print a report of the inventory on screen
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Ask user to enter filename. Create a stream with that file
		
		//Time Program for Performance
		
		
		System.out.print("Enter database filename: ");
		Scanner keyIn = new Scanner(System.in);

		String userFileName = keyIn.nextLine();

		try {
			final long startTime = System.currentTimeMillis();
			File userFile = new File(userFileName);
			Scanner readFile = new Scanner(userFile);

			// Read the entire database
			ArrayList<String> inventory = readInventory(readFile);
			readFile.close();

			// Sort database into appropriate ArrayLists for PostProcessing
			ArrayList<String> prodNames = getItemNames(inventory);
			ArrayList<Integer> prodQuantity = getItemQuantity(inventory);
			ArrayList<Double> prodPrice = getItemPrice(inventory);
			ArrayList<String> prodType = getItemType(inventory);

			// Request Summary Report
			productSummaryReport(prodNames, prodQuantity, prodPrice, prodType);
			inventorySummaryReport(prodNames, prodQuantity, prodPrice, prodType);
			
			//Capture end of program and print execution time summary
			final long endTime = System.currentTimeMillis();
			System.out.println("Total execution time: " + (endTime - startTime) + " ms" );

		}

		catch (IOException e) {
			System.out.println("File with name: " + userFileName + " cannot be opened" + e);
		}

	}

	// Create methods to read contents from file

	private static ArrayList<String> readInventory(Scanner source) {

		ArrayList<String> contents = new ArrayList<String>();

		while (source.hasNext()) {
			String line = source.nextLine();
			contents.add(line);
		}

		return contents;
	}

	// From read contents, sort into appropriate types such as name, price,
	// quantity and type
	private static ArrayList<String> getItemNames(ArrayList<String> contents) {

		ArrayList<String> itemNames = new ArrayList<String>();

		int lenContents = contents.size();

		for (int i = 0; i < lenContents; i = i + 4) {
			String productName = contents.get(i);
			itemNames.add(productName);
		}

		return itemNames;
	}

	private static ArrayList<Integer> getItemQuantity(ArrayList<String> contents) {

		ArrayList<Integer> itemQuantity = new ArrayList<Integer>();

		int lenContents = contents.size();

		for (int i = 1; i < lenContents; i = i + 4) {
			String productQuantity = contents.get(i);
			int prodQuant = Integer.parseInt(productQuantity);
			itemQuantity.add(prodQuant);
		}

		return itemQuantity;

	}

	private static ArrayList<Double> getItemPrice(ArrayList<String> contents) {

		ArrayList<Double> itemPrice = new ArrayList<Double>();

		int lenContents = contents.size();

		for (int i = 2; i < lenContents; i = i + 4) {
			String productPrice = contents.get(i);
			double prodPrice = Double.parseDouble(productPrice);
			itemPrice.add(prodPrice);
		}

		return itemPrice;

	}

	private static ArrayList<String> getItemType(ArrayList<String> contents) {

		ArrayList<String> itemType = new ArrayList<String>();

		int lenContents = contents.size();

		for (int i = 3; i < lenContents; i = i + 4) {
			String productType = contents.get(i);
			itemType.add(productType);
		}

		return itemType;

	}

	public static void productSummaryReport(ArrayList<String> name, ArrayList<Integer> quantity,
			ArrayList<Double> price, ArrayList<String> type) {
		int numItems = name.size();

		System.out.println("Product Summary Report");
		System.out.println("-------------------------------------------------------------------------------------");

		for (int i = 0; i < numItems; i++) {
			System.out.println("Title: " + name.get(i));
			System.out.println("		Product Type: " + type.get(i));
			System.out.println("		Price: " + price.get(i));
			System.out.println("		Quantity: " + quantity.get(i));
			System.out.println("");
		}
	}

	public static void inventorySummaryReport(ArrayList<String> name, ArrayList<Integer> quantity,
			ArrayList<Double> price, ArrayList<String> type) {

		ArrayList<Double> dollarValue = dollarInventory(price, quantity);

		// Find positions of max quantity, max value, min quantity, min value
		int posMaxQuant = findPosMaxInteger(quantity);
		int posMaxValue = findPosMaxDouble(dollarValue);
		int posMinQuant = findPosMinInteger(quantity);
		int posMinValue = findPosMinDouble(dollarValue);

		// Find values corresponding to positions
		String quantMaxValue = name.get(posMaxQuant);
		String quantMaxType = type.get(posMaxQuant);
		String quantMinValue = name.get(posMinQuant);
		String quantMinType = type.get(posMinQuant);
		double valueMax = dollarValue.get(posMaxValue);
		String valueMaxItem = name.get(posMaxValue);
		double valueMin = dollarValue.get(posMinValue);
		String valueMinItem = name.get(posMinValue);

		int itemsInventory = name.size();

		// Output Summary Report
		System.out.println("-------------------------------------------------------------------------------------");
		System.out.println("Total products in database: " + itemsInventory);
		System.out.println("Largest quantity item: " + quantMaxValue + " [" + quantMaxType + "]");
		System.out.println("Highest total dollar item: " + valueMaxItem + " ($" + valueMax + ")");
		System.out.println("Smallest quantity item: " + quantMinValue + " [" + quantMinType + "]");
		System.out.println("Lowest total dollar item: " + valueMinItem + " ($" + valueMin + ")");
		System.out.println("-------------------------------------------------------------------------------------");

	}

	private static ArrayList<Double> dollarInventory(ArrayList<Double> price, ArrayList<Integer> quantity) {
		ArrayList<Double> dollarInventory = new ArrayList<Double>();

		int lenList = price.size();

		for (int i = 0; i < lenList; i++) {
			double value = price.get(i) * quantity.get(i);
			dollarInventory.add(value);
		}

		return dollarInventory;
	}

	private static int findPosMaxInteger(ArrayList<Integer> myList) {
		int posMax = 0;
		int lenList = myList.size();
		int maxVal = myList.get(0);

		for (int i = 0; i < lenList; i++) {
			int curVal = myList.get(i);
			if (curVal > maxVal) {
				maxVal = curVal;
				posMax = i;
			} else {
				// Do nothing
			}
		}
		return posMax;
	}

	private static int findPosMaxDouble(ArrayList<Double> myList) {
		int posMax = 0;
		int lenList = myList.size();
		double maxVal = myList.get(0);

		for (int i = 0; i < lenList; i++) {
			double curVal = myList.get(i);
			if (curVal > maxVal) {
				maxVal = curVal;
				posMax = i;
			} else {
				// Do nothing
			}
		}
		return posMax;
	}

	private static int findPosMinInteger(ArrayList<Integer> myList) {
		int posMin = 0;
		int lenList = myList.size();
		int minVal = myList.get(0);

		for (int i = 0; i < lenList; i++) {
			int curVal = myList.get(i);
			if (curVal < minVal) {
				minVal = curVal;
				posMin = i;
			} else {
				// Do nothing
			}
		}
		return posMin;
	}

	private static int findPosMinDouble(ArrayList<Double> myList) {
		int posMin = 0;
		int lenList = myList.size();
		double minVal = myList.get(0);

		for (int i = 0; i < lenList; i++) {
			double curVal = myList.get(i);
			if (curVal < minVal) {
				minVal = curVal;
				posMin = i;
			} else {
				// Do nothing
			}
		}
		return posMin;
	}

}
