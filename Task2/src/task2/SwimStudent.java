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
public class SwimStudent {

    private String name;
    private String level;
    private SwimLesson group; 
    private boolean waiting;
    private static int id = 0;
    private ArrayList<Qualification> qualifications;

    public SwimStudent(String name) {
        this.name = name;
        this.id = id++;
        this.level = "novice";
    }

    public setLevel(String level){
        this.level = level;
    }
    
    public setGroup
}
