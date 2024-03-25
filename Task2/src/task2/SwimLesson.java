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
public class SwimLesson {

    private static final byte MAX_PARTICIPANTS = 5;
    private String day;
    private String start_time;
    private String level;
    private ArrayList<SwimStudent> participants;
    private Instructor instructor;

    public SwimLesson(String day, String start_time, String level, Instructor instructor) {
        this.day = day;
        this.start_time = start_time;
        this.level = level;
        this.instructor = instructor;
    }

    public void addParticipant(SwimStudent participant) {
        if (participants.size() < MAX_PARTICIPANTS) {
            this.participants.add(participant);
        }
    }

    public ArrayList<SwimStudent> getParticipants() {
        return this.participants;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();

        return str.toString();
    }

    public boolean isFull() {
        if (this.participants.size() == MAX_PARTICIPANTS) {
            return true;
        }
        return false;
    }

}
