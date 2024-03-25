/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package task1;

import java.util.ArrayList;
import java.lang.StringBuilder;

/**
 *
 * @author sebid
 */
public class LiteraturePrize {

    private int year;
    private ArrayList<Laureate> winners;

    public LiteraturePrize() {
        this.winners = new ArrayList<Laureate>();
    }

    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("| " + this.year + " | ");
        for (Laureate l : this.winners) {
            string.append(l.getName());
            string.append(" [");
            for (String s : l.getNations()) {
                string.append(s);
                if (s != l.getNations().get(l.getNations().size() - Base.OFFSET)) {
                    string.append(", ");
                }
            }
            string.append("]");
            if (l != this.winners.get(this.winners.size() - Base.OFFSET)) {
                string.append(", ");
            }
        }
        if (this.winners.size() == 0) {
            string.append("NOT AWARDED");
        }
        return string.toString();
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return this.year;
    }

    public void addLaureate(Laureate winner) {
        this.winners.add(winner);
    }

    public Laureate getLaureate() {
        return this.winners.get(this.winners.size() - Base.OFFSET);
    }

    public ArrayList<Laureate> getWinners() {
        return this.winners;
    }
}
