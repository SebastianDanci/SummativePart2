/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package task1;

import java.util.ArrayList;
import java.lang.StringBuilder;

/**
 *
 * @author sebid
 */
public class LiteraturePrize {

    private int year; // The year of the prize.
    private ArrayList<Laureate> winners; // List of laureates who won this prize.

    // Constructor initializes the list of winners.
    public LiteraturePrize() {
        this.winners = new ArrayList<>();
    }

    // Converts the literature prize details to a string format for display.
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("| ").append(this.year).append(" | "); // Append the year to the string.
        for (Laureate l : this.winners) { // Iterate through each winner.
            string.append(l.getName()); // Append the name of the laureate.
            string.append(" [");
            // Append all nations associated with the laureate, separated by commas.
            for (String s : l.getNations()) {
                string.append(s);
                // Add a comma unless it's the last nation in the list.
                if (!s.equals(l.getNations().get(l.getNations().size() - Base.OFFSET))) {
                    string.append(", ");
                }
            }
            string.append("]");
            // Add a comma unless it's the last laureate in the list.
            if (l != this.winners.get(this.winners.size() - Base.OFFSET)) {
                string.append(", ");
            }
        }
        // If there are no winners, append "NOT AWARDED".
        if (this.winners.isEmpty()) {
            string.append("NOT AWARDED");
        }
        return string.toString();
    }

    // Sets the year of the prize.
    public void setYear(int year) {
        this.year = year;
    }

    // Returns the year of the prize.
    public int getYear() {
        return this.year;
    }

    // Adds a laureate to the list of winners.
    public void addLaureate(Laureate winner) {
        this.winners.add(winner);
    }

    // Returns the most recently added laureate.
    public Laureate getLaureate() {
        return this.winners.get(this.winners.size() - Base.OFFSET);
    }

    // Returns the list of winners for this prize.
    public ArrayList<Laureate> getWinners() {
        return this.winners;
    }
}
