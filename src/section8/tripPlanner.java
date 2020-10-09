package section8;

import acm.graphics.*;
import acm.program.*;
import java.util.*;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.*;

public class tripPlanner extends ConsoleProgram {
	private static int lines1 = 0;
	private static HashMap<String, ArrayList<String>> flightCities = new HashMap<String, ArrayList<String>>();
	private static ArrayList<String> travelPlan = new ArrayList<String>();

	public void run() {

		// selecting file from documents incase you want a different file
		FileDialog dialog = new FileDialog((Frame) null, "Select File to Open");
		dialog.setMode(FileDialog.LOAD);
		dialog.setVisible(true);
		// getting the path of where the file is stored
		String path = dialog.getDirectory();
		path += dialog.getFile();
		println("Where would you like to go? out of the below options");
		// this method calls the the file you selected and reads it into a string
		String file = ReadFile(path);
		// this sits up the hashmap so we can call the data easily
		SettingUpHash(file);
		// printing out the name of every key city in the hasmap
		for (Map.Entry mapElement : flightCities.entrySet()) {
			String name = (String) mapElement.getKey();
			println(name);
		}
		while (true) {
			String StartingPoint = readLine();
			// this is taking the user input and making sure it is in the hashmap
			if (flightCities.containsKey(StartingPoint)) {
				travelPlan.add(StartingPoint);
				// after adding the strarting point into the tracker we go through and check for
				// more plans
				MorePlans();
				break;
			} else {
				println("the city " + StartingPoint + " is not one you can start from");
				println("please enter one of the starting cities");
			}
		}
	}
	//adds all the plans to a list and is ready to return them
	private void MorePlans() {
		int placeholder = 0;
		String trip = "";
		boolean increment = false;

		while (true) {
			int counter = 0;
			println("From " + travelPlan.get(placeholder) + " you can go to the below cities");
			for (String mapElement : flightCities.keySet()) {

				if (mapElement.equals(travelPlan.get(placeholder))) {

					for (String x : flightCities.get(mapElement)) {
						println(x);
					}

				}

			}
			String city = readLine();
			if (flightCities.get(travelPlan.get(placeholder)).contains(city)) {
				placeholder++;
				travelPlan.add(city);
				if (travelPlan.get(0).equals(travelPlan.get(placeholder))) {
					println("the route you've chosen is: ");
					for (int i = 0; i<travelPlan.size(); i++) {
						if(i<travelPlan.size()-1) {
							trip += travelPlan.get(i)+ " -> ";
						}else {
							trip += travelPlan.get(i);
						}
						
					}
					println(trip);
					break;
				}

			} else {
				println(city + "is not one of the cities you can fly to please pick one of the below cities");
			}
		}

	}

	private static ArrayList<String> addList(ArrayList<String> list, String element) {
		ArrayList<String> newList = list;
		newList.add(element);
		return newList;
	}

	private static void SettingUpHash(String file) {
		// split on every line
		for (String line : file.split("\n")) {
			String wordTemp = "";
			boolean frontSwitch = true;
			// split every line into front and back
			for (String word : line.split(" -> ")) {
				// we are looking at front
				if (frontSwitch) {
					// if we don't have that key in hashmap
					if (flightCities.getOrDefault(word, null) == null) {
						// make the thing if it doesn't exist
						flightCities.put(word, new ArrayList<String>());
					}
					// now look at back
					frontSwitch = false;
					// key for use later
					wordTemp = word;
					// we are looking at back
				} else {
					// calls the addlist method to add the new variable to the hashmap
					flightCities.put(wordTemp, addList(flightCities.get(wordTemp), word));
				}
			}
		}

	}

	private static String ReadFile(String path) {
		// TODO Auto-generated method stub
		String data = "";
		String checker = "";
		ArrayList<String> toCities = new ArrayList<String>();

		try {

			File myObj = new File(path);
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				data += myReader.nextLine() + "\n";

			}

			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();

		}
		return data;
	}

}
