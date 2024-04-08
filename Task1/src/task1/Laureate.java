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

    private String name; // Laureate's name
    private BirthDeath birth_death; // Birth and death years
    private ArrayList<String> nations; // List of nations associated with the laureate
    private ArrayList<String> languages; // Languages used by the laureate
    private ArrayList<String> genres; // Genres of literature the laureate has contributed to
    private String citation; // Citation for the award

    // Constructor initializes lists for nations, languages, and genres
    public Laureate() {
        this.nations = new ArrayList<>();
        this.languages = new ArrayList<>();
        this.genres = new ArrayList<>();
    }

    // Converts laureate details to a string for display
    public String toString() {
        final byte NAMELENGTH = 28, YEARLENGTH = 7, LANGUAGELENGTH = 23, GENRELENGTH = 22, PADDING = 14;
        StringBuilder str = new StringBuilder("| ");
        str.append(this.name); // Append the name
        Base.addPadding(str, NAMELENGTH - str.length()); // Adjust padding
        str.append(this.birth_death.toString()); // Append birth and death years
        // Loop through genres and languages, formatting them for display
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
        str = Base.printSeparator(str); // Add a separator line
        Base.printCitation(str, this.citation); // Add the citation
        return str.toString();
    }

    // Sets the laureate's name
    public void setName(String name) {
        this.name = name;
    }

    // Returns the laureate's name
    public String getName() {
        return this.name;
    }

    // Sets the laureate's birth and death years
    public void setDate(String date) {
        this.birth_death = new BirthDeath(date);
    }

    // Adds a nation to the list of nations
    public void addNation(String nation) {
        this.nations.add(nation);
    }

    // Returns the list of nations
    public ArrayList<String> getNations() {
        return this.nations;
    }

    // Adds a language to the list of languages
    public void addLanguage(String language) {
        this.languages.add(language);
    }

    // Adds a genre to the list of genres
    public void addGenre(String genre) {
        this.genres.add(genre);
    }

    // Sets the citation for the award
    public void setCitation(String citation) {
        this.citation = citation;
    }

    // Returns the list of genres
    public ArrayList<String> getGenre() {
        return this.genres;
    }
}
