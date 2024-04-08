/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package task1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author sebid
 */
public class ReadFile {

    // Attempts to read the data from a specified path and parse it.
    public static boolean readFile(String dataPath) {
        try {
            File file = new File(dataPath); // Create a File instance for the specified path.
            Scanner reader = new Scanner(file); // Open the file for reading.
            parseData(reader); // Parse the data using the opened file.
            return true; // Return true if reading and parsing are successful.
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred."); // Print an error message if the file is not found.
            e.printStackTrace(); // Print the stack trace for debugging.
            return false; // Return false if an error occurs.
        }
    }

    // Parses the data from the Scanner object.
    private static void parseData(Scanner reader) {

        LiteraturePrize currentPrize = new LiteraturePrize(); // Initialize a new LiteraturePrize object.

        if (!reader.hasNextLine()) {
            System.out.println("Error: The file is empty!"); // Check if the file is empty.
            return; // Exit if the file is empty.
        }

        String data = reader.nextLine(); // Read the first line (expected to be the year).
        currentPrize.setYear(Integer.parseInt(data)); // Set the year for the current prize.
        data = reader.nextLine(); // Read the next line to proceed with parsing.

        // Loop through the file lines.
        while (reader.hasNextLine()) {

            if (data.charAt(0) == '-') { // Check if it's the start of a new prize entry.
                Base.addPrize(currentPrize); // Add the filled prize object to the collection.
                currentPrize = new LiteraturePrize(); // Create a new LiteraturePrize object for the next entry.
                currentPrize.setYear(Integer.parseInt(reader.nextLine().trim())); // Set the year for the new prize.
                data = reader.nextLine(); // Read the next line to continue.
            }

            if (data.contains("Not awarded")) { // Check if the prize was not awarded for the year.
                data = reader.nextLine(); // Skip to the next line.
                continue; // Continue to the next iteration of the loop.
            }

            parseBio(data, currentPrize); // Parse the biography details from the current line.
            currentPrize.getLaureate().setCitation(reader.nextLine()); // Set the citation for the laureate.

            // Parse and add genres from the next line.
            for (String s : stringParser(reader.nextLine(), ",")) {
                currentPrize.getLaureate().addGenre(s);
            }

            data = reader.nextLine(); // Read the next line to continue the loop.
        }
        Base.addPrize(currentPrize); // Add the last prize object to the collection.
        reader.close(); // Close the reader.
        Base.sortArray(); // Sort the collection of prizes.
    }

    // Parses biography details and sets them to the current prize object.
    private static void parseBio(String data, LiteraturePrize currentPrize) {
        currentPrize.addLaureate(new Laureate()); // Add a new Laureate object to the current prize.
        currentPrize.getLaureate().setName(data.substring(0, (data.indexOf('(')) - Base.OFFSET)); // Extract and set the name of the laureate.
        currentPrize.getLaureate().setDate(data.substring(data.indexOf('(') + Base.OFFSET, data.indexOf(')'))); // Extract and set the date of birth/death.
        // Extract and add nations to the laureate.
        for (String s : stringParser(data.substring(data.indexOf('|') + Base.OFFSET, data.lastIndexOf('|')), ",")) {
            currentPrize.getLaureate().addNation(s);
        }
        // Extract and add languages to the laureate.
        for (String s : stringParser(data.substring(data.lastIndexOf('|') + Base.OFFSET), ",")) {
            currentPrize.getLaureate().addLanguage(s);
        }
    }

    // Splits a string by a specified delimiter and returns an ArrayList of the parts.
    private static ArrayList<String> stringParser(String string, String split) {
        return new ArrayList<>(Arrays.asList(string.split(split)));
    }

}
