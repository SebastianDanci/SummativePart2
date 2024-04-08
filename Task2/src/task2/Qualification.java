/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package task2;

/**
 *
 * @author sebid
 */
// Abstract class representing a qualification awarded to a swim student
public abstract class Qualification {

    private Instructor awardedBy; // Instructor who awarded the qualification
    private SwimStudent awardedTo; // Swim student who received the qualification

    // Constructor for a qualification
    public Qualification(Instructor awardedBy, SwimStudent awardedTo) {
        this.awardedBy = awardedBy; // Set the instructor who awarded the qualification
        this.awardedTo = awardedTo; // Set the swim student who received the qualification
    }

    // Returns the instructor who awarded the qualification
    public Instructor getAwardedBy() {
        return awardedBy;
    }

    // Returns the swim student who received the qualification
    public SwimStudent getAwardedTo() {
        return awardedTo;
    }
}
