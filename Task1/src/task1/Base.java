/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package task1;

import java.util.ArrayList;
import java.lang.StringBuilder;
import java.util.Scanner;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author sebid
 */
public class Base {

    private static ArrayList<LiteraturePrize> prizes = new ArrayList<LiteraturePrize>(); // Holds all literature prizes
    private static Scanner input = new Scanner(System.in); // Scanner for reading user input
    private static int firstPrize, lastPrize = -1; // Tracks the first and last year prizes were awarded
    public static final String HEADER = "| Winner(s)                 | Born | Died | Language(s)           | Genre(s)             |"; // Header for displaying laureate details
    public static final byte OFFSET = 1; // Offset used for formatting

    // Adds a prize to the list and updates first and last prize years
    public static void addPrize(LiteraturePrize prize) {
        if (lastPrize == -1) {
            firstPrize = prize.getYear(); // Initializes first prize year if it's the first prize added
        }
        if (prize.getYear() > lastPrize) {
            lastPrize = prize.getYear(); // Updates last prize year if it's later than the current last prize
        }
        if (prize.getYear() < firstPrize) {
            firstPrize = prize.getYear(); // Updates first prize year if it's earlier than the current first prize
        }
        prizes.add(prize); // Adds the prize to the list
    }

    // Returns the list of all prizes
    public static ArrayList<LiteraturePrize> getPrizes() {
        return prizes;
    }

    // Prints the main menu options
    private static void printOptions() {
        final int CONTENTWIDTH = 21; // Width for the content separator
        System.out.println("\n\n");
        printSeparator(CONTENTWIDTH); // Prints top separator
        System.out.println("Literature prize menu"); // Menu title
        printSeparator(CONTENTWIDTH); // Prints separator under the title
        System.out.println("List ...............1"); // Option 1
        System.out.println("Select .............2"); // Option 2
        System.out.println("Search .............3"); // Option 3
        System.out.println("Exit ...............0"); // Option 0
        printSeparator(CONTENTWIDTH); // Prints bottom separator
        System.out.print("Enter choice > "); // Prompt for user input
    }

    // Displays a list of prizes within a given year range
    private static void menuList() {
        final String COLUMNHEADER = "| Year | Prize Winners (and associated nations)"; // Column header for the list
        int start, end, contentWidth = 0; // Variables for start/end years and content width
        final int PADDING = 7; // Padding for formatting

        start = readYear("\nEnter start year > ") - prizes.get(0).getYear(); // Reads and calculates start year index
        end = readYear("\nEnter end year > ") - prizes.get(0).getYear(); // Reads and calculates end year index
        if (start > end) {
            // If start year is greater than end year, swap them
            end = (start + end) - (start = end);
        }
        for (int i = start; i <= end; i++) {
            // Determines the maximum content width needed
            int size = prizes.get(i).toString().length();
            if (contentWidth < size) {
                contentWidth = size;
            }
        }
        if (contentWidth < COLUMNHEADER.length()) {
            // Ensures content width is at least as wide as the header
            contentWidth = COLUMNHEADER.length();
        }
        contentWidth += PADDING; // Adds padding to the content width
        StringBuilder str = new StringBuilder(); // StringBuilder for constructing display strings
        printSeparator(contentWidth); // Prints top separator
        str.setLength(0); // Resets StringBuilder
        str.append(COLUMNHEADER); // Appends column header
        addPadding(str, contentWidth - COLUMNHEADER.length()); // Adds padding to align with the content width
        System.out.println(str); // Prints the header
        str.setLength(0); // Resets StringBuilder
        printSeparator(contentWidth); // Prints separator under the header
        for (int i = start; i <= end; i++) {
            // Loops through each prize in the specified range and prints its details
            str.setLength(0); // Resets StringBuilder
            str.append(prizes.get(i).toString()); // Appends prize details
            addPadding(str, contentWidth - prizes.get(i).toString().length()); // Adds padding to align with the content width
            System.out.println(str.toString()); // Prints the prize details
        }
        str.setLength(0); // Resets StringBuilder
        printSeparator(contentWidth); // Prints bottom separator
    }

    private static void menuSelect() {
        // Calculates the index of the selected year based on user input
        final int CHOICE = readYear("\nEnter year > ") - prizes.get(0).getYear();
        // Checks if no prize was awarded in the selected year and exits if true
        if (prizes.get(CHOICE).getWinners().size() == 0) {
            System.out.println("NO PRIZE AWARDED THIS YEAR\n\n");
            return;
        }
        // Prints a separator at the top of the listing
        printSeparator(HEADER.length() - OFFSET);
        // Prints the header defined in the Base class
        System.out.println(HEADER);
        // Prints another separator after the header
        printSeparator(HEADER.length() - OFFSET);
        // Iterates over each laureate who won in the selected year and prints their details
        for (Laureate l : prizes.get(CHOICE).getWinners()) {
            System.out.println(l.toString()); // Prints the laureate's details
            printSeparator(HEADER.length() - OFFSET); // Prints a separator after each laureate's details
        }
        System.out.println("\n"); // Prints a newline for formatting purposes after the listing
    }

    private static void menuSearch() {
        // Define column headers
        final String NAME = "| Name", GENRES = "Genres", YEAR = "Year";
        final int PADDING = 3; // Padding between columns
        int longestLength = 0, longestName = 0; // Track the longest genre list and the longest name for formatting
        System.out.print("\nEnter search term for writing genre > ");
        ArrayList<Laureate> matches = new ArrayList<Laureate>(); // Store matching laureates
        ArrayList<Integer> years = new ArrayList<Integer>(); // Store years corresponding to matches
        String search = input.nextLine().toLowerCase(); // Get the search term and convert to lowercase for case-insensitive search
        // Iterate through all prizes and their laureates to find matches
        for (LiteraturePrize p : prizes) {
            for (Laureate l : p.getWinners()) {
                int currentLength = 0; // Track the length of the concatenated genres string
                boolean hasMatch = false; // Flag to indicate if a match is found
                // Check each genre of the laureate for the search term
                for (String s : l.getGenre()) {
                    currentLength += s.length() + 1; // Increment the length for each genre (including separator)
                    if (s.contains(search)) {
                        matches.add(l); // Add laureate to matches
                        years.add(p.getYear()); // Add corresponding year to years
                        hasMatch = true; // Set flag to true
                    }
                }
                // Update longestLength and longestName for formatting purposes
                if (currentLength > longestLength && hasMatch) {
                    longestLength = currentLength;
                }
                if (hasMatch && longestName < l.getName().length()) {
                    longestName = l.getName().length();
                }
            }
        }
        // If no matches found, print a message and exit the method
        if (matches.size() == 0) {
            System.out.println("\nThere are no matches found for the given term :'" + search + "'\n");
            return;
        }
        // Create a list of indices to sort matches by name
        List<Integer> indices = new ArrayList<Integer>();
        for (int i = 0; i < matches.size(); i++) {
            indices.add(i);
        }
        // Sort indices based on the names of the laureates in matches
        Collections.sort(indices, new Comparator<Integer>() {
            public int compare(Integer i1, Integer i2) {
                return matches.get(i1).getName().compareTo(matches.get(i2).getName());
            }
        });
        // Use sorted indices to create sorted lists of matches and years
        List<Laureate> sortedMatches = new ArrayList<Laureate>(matches.size());
        List<Integer> sortedYears = new ArrayList<Integer>(years.size());
        for (int index : indices) {
            sortedMatches.add(matches.get(index));
            sortedYears.add(years.get(index));
        }
        // Clear original lists and repopulate with sorted data
        matches.clear();
        matches.addAll(sortedMatches);
        years.clear();
        years.addAll(sortedYears);
        // Ensure longestLength is at least as long as the "Genres" header for formatting
        if (longestLength < GENRES.length()) {
            longestLength = GENRES.length();
        }
        // Begin building the output string
        StringBuilder str = new StringBuilder();
        // Print header with dynamic sizing based on content
        printSeparator(str, longestLength + longestName + OFFSET * 9 + PADDING * 2 + YEAR.length());
        str.append(NAME);
        addPadding(str, longestName + PADDING - NAME.length() + OFFSET * 2);
        str.append(" " + GENRES);
        addPadding(str, longestLength + PADDING - GENRES.length() + OFFSET);
        str.append(" " + YEAR);
        addPadding(str, OFFSET);
        // Print separator after the header
        printSeparator(str, longestLength + longestName + OFFSET * 9 + PADDING * 2 + YEAR.length());
        // Iterate through matches to build and print each row
        for (int i = 0; i < matches.size(); i++) {
            str.append("| " + matches.get(i).getName());
            addPadding(str, PADDING + longestName - matches.get(i).getName().length());
            str.append(" ");
            int currentLength = 0;
            // Append genres, highlighting the search term
            for (String s : matches.get(i).getGenre()) {
                if (s.contains(search)) {
                    str.append(s.substring(0, s.indexOf(search)));
                    str.append(s.substring(s.indexOf(search), s.indexOf(search) + search.length()).toUpperCase());
                    str.append(s.substring(s.indexOf(search) + search.length()) + ",");
                } else {
                    str.append(s + ",");
                }
                currentLength += s.length() + 1;
            }
            // Remove trailing comma and add padding for alignment
            str.deleteCharAt(str.length() - 1);
            addPadding(str, PADDING + longestLength - currentLength + OFFSET * 2);
            // Append year and final padding
            addPadding(str.append(" " + years.get(i)), OFFSET);
            // Print separator for each row
            printSeparator(str, longestLength + longestName + OFFSET * 9 + PADDING * 2 + YEAR.length());
        }
        // Print the completed string
        System.out.println(str);
    }

    public static int readYear(String message) {
        int integer = -1; // Initialize the year to an invalid value

        while (true) {
            try {
                System.out.print(message); // Display the prompt message
                integer = Integer.parseInt(input.nextLine().trim()); // Parse the input year, trimming whitespace
                // Check if the input year is within the range of years in the database
                if (integer < prizes.get(0).getYear() || integer > prizes.get(prizes.size() - OFFSET).getYear()) {
                    // Throw an exception if the year is out of range
                    throw new ArithmeticException("\n\nError: Year not in database;\nTry a number between: " + prizes.get(0).getYear() + " - " + prizes.get(prizes.size() - OFFSET).getYear());
                }
                break; // Exit the loop if a valid year is entered
            } catch (ArithmeticException iae) {
                System.out.println(iae.getMessage()); // Print the error message if the year is out of range
            } catch (Exception e) {
                System.out.println("\n\nError: Expected integer input;"); // Print the error message for non-integer inputs
            }
        }
        return integer; // Return the validated year
    }

    public static void runMenu() {
        String choice = "1"; // Default choice to enter the menu loop

        while (!choice.equals("0")) { // Continue showing the menu until the user chooses to exit
            printOptions(); // Display the menu options
            choice = input.nextLine(); // Read the user's choice
            switch (choice) { // Execute the appropriate method based on the user's choice
                case "1":
                    menuList(); // Display the list of literature prizes
                    break;
                case "2":
                    menuSelect(); // Select and display a specific year's prize details
                    break;
                case "3":
                    menuSearch(); // Search and display laureates based on a genre search term
                    break;
                case "0":
                    System.out.println("\n\nExiting...\n"); // Exit the program
                    break;
                default:
                    System.out.println("Invalid choice"); // Handle invalid menu choices
                    break;
            }
        }
    }

    public static void sortArray() {
        // Sort the prizes array by year in ascending order
        Collections.sort(prizes, new Comparator<LiteraturePrize>() {
            public int compare(LiteraturePrize p1, LiteraturePrize p2) {
                return Integer.compare(p1.getYear(), p2.getYear());
            }
        });
    }

    public static void printSeparator(int contentWidth) {
        StringBuilder str = new StringBuilder();
        // Append dashes equal to the content width for creating a separator line
        for (int i = 0; i <= contentWidth; i++) {
            str.append("-");
        }
        System.out.println(str); // Print the separator line
    }

    public static StringBuilder printSeparator(StringBuilder str) {
        // Append a new line followed by dashes equal to the HEADER's length for creating a separator line
        str.append("\n");
        for (int i = 0; i <= Base.HEADER.length() - 1; i++) {
            str.append("-");
        }
        str.append("\n");
        return str; // Return the updated StringBuilder with the separator
    }

    public static StringBuilder printSeparator(StringBuilder str, int length) {
        // Append a new line followed by dashes equal to the specified length for creating a custom separator line
        str.append("\n");
        for (int i = 0; i <= length - 1; i++) {
            str.append("-");
        }
        str.append("\n");
        return str; // Return the updated StringBuilder with the separator
    }

    public static void addPadding(StringBuilder str, int padding) {
        // Append spaces equal to the padding amount for formatting purposes
        for (int i = 0; i < padding; i++) {
            str.append(" ");
        }
        str.append("|"); // Append a vertical bar at the end to close the padding section
    }

    public static void printCitation(StringBuilder str, String citation) {
        // Calculate the width of the box based on HEADER's length
        final int boxWidth = HEADER.length();
        // Calculate the inner width for the citation text, leaving margins
        final int innerWidth = boxWidth - 20;
        // Generate the top and bottom border of the box
        final String topBottomBorder = repeat("-", boxWidth);
        // Generate an empty line within the box
        final String emptyLine = "|" + repeat(" ", boxWidth - 2) + "|";

        // Center the title "Citation:" within the box
        String centeredCitationTitle = centerTextInBox("Citation:", boxWidth, 10);
        str.append(centeredCitationTitle).append("\n");

        // Append an empty line below the title
        str.append(emptyLine).append("\n");

        // Split the citation text into words for line wrapping
        String[] words = citation.split(" ");
        StringBuilder line = new StringBuilder();
        for (String word : words) {
            // Append words to the line until it reaches the inner width
            if (line.length() + word.length() + 1 <= innerWidth) {
                if (line.length() > 0) {
                    line.append(" ");
                }
                line.append(word);
            } else {
                // If the line reaches the inner width, center it in the box and start a new line
                str.append(centerTextInBox(line.toString(), boxWidth, 10)).append("\n");
                line = new StringBuilder(word);
            }
        }
        // Append any remaining text in the line
        if (line.length() > 0) {
            str.append(centerTextInBox(line.toString(), boxWidth, 10)).append("\n");
        }

        // Append an empty line at the bottom of the citation box
        str.append(emptyLine);
    }

    private static String centerTextInBox(String text, int boxWidth, int paddingPerSide) {
        // Calculate padding needed to center the text within the box, considering padding on each side
        int textPadding = (boxWidth - 2 * paddingPerSide - text.length()) / 2;
        // Ensure that padding is at least as large as paddingPerSide, to maintain the specified padding
        String padding = repeat(" ", Math.max(textPadding + paddingPerSide, paddingPerSide));
        // Return the centered text within the box, ensuring total width matches boxWidth
        return "|" + padding + text + repeat(" ", boxWidth - 2 - padding.length() - text.length()) + "|";
    }

    public static String repeat(String str, int count) {
        StringBuilder sb = new StringBuilder();
        // Append the string 'str' to itself 'count' times
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString(); // Return the concatenated string
    }

}
