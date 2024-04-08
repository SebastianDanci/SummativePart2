/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package task2;

/**
 *
 * @author sebid
 */
// Extends Qualification to represent a distance swim qualification
public class DistanceSwim extends Qualification {

    private int distance; // Distance achieved in the swim

    // Constructor initializing distance swim with instructor, student, and distance
    public DistanceSwim(Instructor awardedBy, SwimStudent awardedTo, int distance) {
        super(awardedBy, awardedTo); // Call to the superclass (Qualification) constructor
        this.distance = distance; // Set the distance achieved
    }

    // Getter for the distance
    public int getDistance() {
        return distance; // Return the distance achieved in this qualification
    }
}
