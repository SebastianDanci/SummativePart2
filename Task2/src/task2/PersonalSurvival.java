/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package task2;

/**
 *
 * @author sebid
 */
// Class representing a personal survival qualification extending the abstract Qualification class
public class PersonalSurvival extends Qualification {

    private String category; // Category of personal survival qualification (e.g., Bronze, Silver, Gold)

    // Constructor for a personal survival qualification
    public PersonalSurvival(Instructor awardedBy, SwimStudent awardedTo, String category) {
        super(awardedBy, awardedTo); // Call the superclass constructor with instructor and student
        this.category = category; // Set the category of the personal survival qualification
    }

    // Returns the category of the personal survival qualification
    public String getCategory() {
        return category;
    }
}
