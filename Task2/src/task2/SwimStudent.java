/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package task2;

import java.lang.StringBuilder;
import java.util.ArrayList;

/**
 *
 * @author sebid
 */
// Represents a student at the swim school
public class SwimStudent {

    private String name; // Student's name
    private String level; // Current level of the student (novice, improver, advanced)
    private SwimLesson group; // The group or class the student is assigned to
    private boolean waiting; // Flag indicating if the student is waiting for assignment
    private static int ids = 1; // Static counter for generating unique IDs
    private int id; // Unique ID for each student
    private ArrayList<Qualification> qualifications = new ArrayList<>(); // List of qualifications earned by the student

    // Constructor for a new student with only a name (defaults to novice level)
    public SwimStudent(String name) {
        this.name = name;
        this.id = ids++; // Increment and assign ID
        this.level = "novice"; // Default level
    }

    // Constructor for a new student specifying name, level, and qualifications
    public SwimStudent(String name, String level, ArrayList<Qualification> qualifications) {
        this.name = name;
        this.id = ids++; // Increment and assign ID
        this.level = level; // Set level
        this.qualifications = qualifications; // Assign qualifications
    }

    // Constructor for a new student assigned to a group
    public SwimStudent(String name, SwimLesson group) {
        this.name = name;
        this.id = ids++; // Increment and assign ID
        this.level = "novice"; // Default level
        this.group = group; // Set group
        group.addParticipant(this); // Add this student to the group
    }

    // Sets the student's level
    public void setLevel(String level) {
        this.level = level;
    }

    // Assigns the student to a group
    public void setGroup(SwimLesson group) {
        this.group = group;
        group.addParticipant(this); // Add this student to the group
    }

    // Returns the group the student is assigned to
    public SwimLesson getGroup() {
        return this.group;
    }

    // Returns the list of qualifications earned by the student
    public ArrayList<Qualification> getQualifications() {
        return this.qualifications;
    }

    // toString method to provide detailed information about the student
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("\nID: ").append(id).append("\nName: ").append(name).append("\nLevel: ").append(level).append("\n");

        if (group != null) { // Include class information if assigned
            str.append("Class Day: ").append(group.getDay())
                    .append(", Time: ").append(group.getTime())
                    .append("\nInstructor: ").append(group.getInstructor().getName()).append("\n");
        } else {
            str.append("Class: Not assigned\n"); // Indicate not assigned to a class
        }

        if (waiting) { // Include waiting status
            str.append("Status: Waiting for assignment\n");
        }

        // Handle displaying qualifications
        if (!qualifications.isEmpty()) {
            str.append("Qualifications:\n");
            for (Qualification qualification : qualifications) {
                if (qualification instanceof DistanceSwim) {
                    DistanceSwim distanceSwim = (DistanceSwim) qualification;
                    str.append(" - Distance Swim: ").append(distanceSwim.getDistance()).append("m, Awarded by: ").append(distanceSwim.getAwardedBy().getName()).append("\n");
                } else if (qualification instanceof PersonalSurvival) {
                    PersonalSurvival personalSurvival = (PersonalSurvival) qualification;
                    str.append(" - Personal Survival: ").append(personalSurvival.getCategory()).append(", Awarded by: ").append(personalSurvival.getAwardedBy().getName()).append("\n");
                }
            }
        } else {
            str.append("Qualifications: None\n");
        }

        return str.toString();
    }

    // Getters for name, ID, level, and waiting status
    public String getName() {
        return this.name;
    }

    public int getID() {
        return this.id;
    }

    public String getLevel() {
        return this.level;
    }

    public void setWaiting(boolean bool) {
        this.waiting = bool;
    }

    public boolean getWaiting() {
        return this.waiting;
    }

}
