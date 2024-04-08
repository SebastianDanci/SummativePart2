/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package task2;

import java.util.ArrayList;
import java.lang.StringBuilder;

/**
 *
 * @author sebid
 */
// Represents a swim lesson within the swim school
public class SwimLesson {

    public static final byte MAX_PARTICIPANTS = 5; // Maximum number of participants allowed in a lesson
    private String day; // Day the lesson is held
    private String start_time; // Start time of the lesson
    private String level; // Level of the lesson (novice, improver, advanced)
    private ArrayList<SwimStudent> participants = new ArrayList<>(); // List of participants in the lesson
    private Instructor instructor; // Instructor teaching the lesson

    // Constructor for a swim lesson
    public SwimLesson(String day, String start_time, String level, Instructor instructor) {
        this.day = day;
        this.start_time = start_time;
        this.level = level;
        this.instructor = instructor;
        instructor.addLesson(this); // Associate this lesson with the instructor
    }

    // Adds a participant to the lesson if not full
    public void addParticipant(SwimStudent participant) {
        if (participants.size() < MAX_PARTICIPANTS) {
            participants.add(participant);
        }
    }

    // Returns the list of participants
    public ArrayList<SwimStudent> getParticipants() {
        return participants;
    }

    // Checks if the lesson is full
    public boolean isFull() {
        return participants.size() == MAX_PARTICIPANTS;
    }

    // Returns the day of the lesson
    public String getDay() {
        return day;
    }

    // Returns the start time of the lesson
    public String getTime() {
        return start_time;
    }

    // Returns the instructor of the lesson
    public Instructor getInstructor() {
        return instructor;
    }

    // Returns the level of the lesson
    public String getLevel() {
        return level;
    }

    // toString method to provide detailed information about the lesson
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Day: ").append(day).append(", Time: ").append(start_time).append("\n");
        str.append("Instructor: ").append(instructor.getName()).append("\n");

        // Display participants or indicate the class is empty
        if (participants.isEmpty()) {
            str.append("No students currently allocated to this class.\n");
        } else {
            str.append("Students currently allocated to this class:\n");
            participants.forEach(student -> str.append("- ").append(student.getName()).append("\n"));
        }

        // Indicate if the class is full or spaces available
        if (isFull()) {
            str.append("This class is currently full.\n");
        } else {
            int availableSpaces = MAX_PARTICIPANTS - participants.size();
            str.append("There are ").append(availableSpaces).append(" spaces available for this class.\n");
        }

        return str.toString();
    }

    // Removes a participant by their ID
    public void removeParticipant(int studentID) {
        participants.removeIf(student -> student.getID() == studentID);
    }

}
