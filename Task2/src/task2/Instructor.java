/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package task2;

import java.util.ArrayList;

/**
 *
 * @author sebid
 */
// Defines the Instructor class that teaches swim lessons
public class Instructor {

    private String name; // Instructor's name
    private ArrayList<SwimLesson> lessonsTeached = new ArrayList<>(); // List of lessons taught by the instructor

    // Constructor that sets the instructor's name
    public Instructor(String name) {
        this.name = name;
    }

    // Adds a swim lesson to the list of lessons this instructor teaches
    public void addLesson(SwimLesson lesson) {
        this.lessonsTeached.add(lesson);
    }

    // Returns the instructor's name
    public String getName() {
        return this.name;
    }

    // toString representation of the instructor's schedule
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("\n").append(name).append("'s Schedule:\n");

        // Iterates over each lesson taught by the instructor
        for (SwimLesson lesson : lessonsTeached) {
            str.append("\n\nDay: ").append(lesson.getDay()).append("\nTime: ").append(lesson.getTime()).append("\n");
            str.append("Level: ").append(lesson.getLevel()).append("\n");

            // Lists students participating in each class
            if (!lesson.getParticipants().isEmpty()) {
                str.append("Students currently allocated to this class:\n");
                for (SwimStudent student : lesson.getParticipants()) {
                    str.append("- ").append(student.getName()).append("\n");
                }
            } else {
                str.append("No students currently allocated to this class.\n");
            }

            // Indicates if the class is full
            if (lesson.isFull()) {
                str.append("This class is currently full.\n");
            } else {
                int availableSpaces = SwimLesson.MAX_PARTICIPANTS - lesson.getParticipants().size();
                str.append("There are ").append(availableSpaces).append(" spaces available for this class.\n");
            }
        }
        return str.toString();
    }

    // Returns the list of lessons taught by the instructor
    public ArrayList<SwimLesson> getLessonsTeached() {
        return this.lessonsTeached;
    }
}
