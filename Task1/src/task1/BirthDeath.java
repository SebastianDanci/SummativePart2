/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package task1;

/**
 *
 * @author sebid
 */
public class BirthDeath {

    private String birth; // Birth year
    private String death; // Death year or "----" if not applicable

    // Constructor processes the input string to extract birth and death years
    public BirthDeath(String date) {
        if (date.length() < 9) {
            // Handles cases where the format does not include death year
            this.birth = date.substring(0, 4);
            this.death = Base.repeat(" ", 9 - date.length()) + date.substring(5);
            return;
        }
        if (date.charAt(0) == 'b') {
            // Handles cases marked with 'b' for birth only
            this.death = "----"; // Marks death year as not applicable
            this.birth = date.substring(3); // Extracts the birth year
        } else {
            // Regular case with both birth and death years
            this.birth = date.substring(0, 4); // Extracts the birth year
            this.death = date.substring(5); // Extracts the death year
        }
    }

    // Converts the birth and death years to a string format
    public String toString() {
        return " " + this.birth + " | " + this.death + " |";
    }
}
