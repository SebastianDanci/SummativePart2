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
public class Laureate {

    private String name;
    private BirthDeath birth_death;
    private ArrayList<String> nations;
    private ArrayList<String> languages;
    private ArrayList<String> genres;
    private String citation;

    public Laureate() {
        this.nations = new ArrayList<String>();
        this.languages = new ArrayList<String>();
        this.genres = new ArrayList<String>();
    }

    public String toString() {
        final byte NAMELENGTH = 28, YEARLENGTH = 7, LANGUAGELENGTH = 23, GENRELENGTH = 22, PADDING = 14;
        final String BORNDEATH = "      |      ";
        StringBuilder str = new StringBuilder("| ");
        str.append(this.name);
        Base.addPadding(str, NAMELENGTH - str.length());
        str.append(this.birth_death.toString());
        for (byte count = 0; count < this.genres.size() || count < this.languages.size(); count++) {
            if (count == 0 || (count < this.genres.size() && count < this.languages.size())) {
                if (count != 0) {
                    str.append("\n|");
                    Base.addPadding(str, NAMELENGTH - Base.OFFSET);
                    Base.addPadding(str, YEARLENGTH - Base.OFFSET);
                    Base.addPadding(str, YEARLENGTH - Base.OFFSET);
                }
                str.append(" " + this.languages.get(count));
                Base.addPadding(str, LANGUAGELENGTH - this.languages.get(count).length() - Base.OFFSET);
                if (count != 0) {
                    str.append(this.genres.get(count));
                    Base.addPadding(str, GENRELENGTH - this.genres.get(count).length());
                } else {
                    str.append(" " + this.genres.get(count));
                    Base.addPadding(str, GENRELENGTH - this.genres.get(count).length() - Base.OFFSET);
                }
            } else {
                str.append("\n|");
                Base.addPadding(str, NAMELENGTH - Base.OFFSET);
                Base.addPadding(str, YEARLENGTH - Base.OFFSET);
                Base.addPadding(str, YEARLENGTH - Base.OFFSET);
                if (count < this.genres.size()) {
                    Base.addPadding(str, LANGUAGELENGTH);
                    str.append(this.genres.get(count));
                    Base.addPadding(str, GENRELENGTH - this.genres.get(count).length());
                }
                if (count < this.languages.size()) {
                    str.append(" " + this.languages.get(count));
                    Base.addPadding(str, LANGUAGELENGTH - this.languages.get(count).length() - Base.OFFSET);
                    Base.addPadding(str, GENRELENGTH);
                }
            }
        }
        str = Base.printSeparator(str);
        Base.printCitation(str, this.citation);
        return str.toString();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setDate(String date) {

        this.birth_death = new BirthDeath(date);
    }

    public void addNation(String nation) {
        this.nations.add(nation);
    }

    public ArrayList<String> getNations() {
        return this.nations;
    }

    public void addLanguage(String language) {
        this.languages.add(language);
    }

    public void addGenre(String genre) {
        this.genres.add(genre);
    }

    public void setCitation(String citation) {
        this.citation = citation;
    }

    public ArrayList<String> getGenre() {
        return this.genres;
    }
}
