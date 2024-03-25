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

    private String birth;
    private String death;

    public BirthDeath(String date) {
        if (date.length() < 9) {
            this.birth = date.substring(0, 4);
            this.death = Base.repeat(" ", 9 - date.length()) + date.substring(5);
            return;
        }
        if (date.charAt(0) == 'b') {
            this.death = "----";
            this.birth = date.substring(3);
        } else {
            this.birth = date.substring(0, 4);
            this.death = date.substring(5);
        }
    }

    public String toString() {
        return " " + this.birth + " | " + this.death + " |";
    }
}
