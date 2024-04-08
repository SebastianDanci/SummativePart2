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
// Manages waiting lists for new and promoted swim students
public class WaitingList {

    // Stores new students waiting to join
    private static ArrayList<SwimStudent> newStudents = new ArrayList<>();
    // Stores promoted students waiting for the next level
    private static ArrayList<SwimStudent> promotedStudents = new ArrayList<>();

    // Adds a new student to the waiting list
    public static void addNew(SwimStudent student) {
        newStudents.add(student);
    }

    // Adds a promoted student to the waiting list
    public static void addPromoted(SwimStudent student) {
        promotedStudents.add(student);
    }

    // Returns the list of new students
    public static ArrayList<SwimStudent> getNew() {
        return newStudents;
    }

    // Returns the list of promoted students
    public static ArrayList<SwimStudent> getPromoted() {
        return promotedStudents;
    }

    // Removes a student by ID from both new and promoted lists
    public static void removeStudentById(int studentId) {
        newStudents.removeIf(student -> student.getID() == studentId); // Remove from new students list
        promotedStudents.removeIf(student -> student.getID() == studentId); // Remove from promoted students list
    }
}
