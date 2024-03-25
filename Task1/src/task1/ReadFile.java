/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package task1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author sebid
 */
public class ReadFile {

    public static boolean readFile(String dataPath) {
        try {
            File file = new File(dataPath);
            Scanner reader = new Scanner(file);
            parseData(reader);
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return false;
        }
    }

    private static void parseData(Scanner reader) {

        LiteraturePrize currentPrize = new LiteraturePrize();

        if (!reader.hasNextLine()) {
            System.out.println("Error: The file is empty!");
            return;
        }

        String data = reader.nextLine();
        currentPrize.setYear(Integer.parseInt(data));
        data = reader.nextLine();

        while (reader.hasNextLine()) {

            if (data.charAt(0) == '-') {
                Base.addPrize(currentPrize);
                currentPrize = new LiteraturePrize();
                currentPrize.setYear(Integer.parseInt(reader.nextLine().trim()));
                data = reader.nextLine();
            }

            if (data.contains("Not awarded")) {
                data = reader.nextLine();
                continue;
            };

            parseBio(data, currentPrize);
            currentPrize.getLaureate().setCitation(reader.nextLine());

            for (String s : stringParser(reader.nextLine(), ",")) {
                currentPrize.getLaureate().addGenre(s);
            }

            data = reader.nextLine();
        }
        Base.addPrize(currentPrize);
        reader.close();
        Base.sortArray();
    }

    private static void parseBio(String data, LiteraturePrize currentPrize) {
        currentPrize.addLaureate(new Laureate());
        currentPrize.getLaureate().setName(data.substring(0, (data.indexOf('(')) - Base.OFFSET));
        currentPrize.getLaureate().setDate(data.substring(data.indexOf('(') + Base.OFFSET, data.indexOf(')')));
        for (String s : stringParser(data.substring(data.indexOf('|') + Base.OFFSET, data.lastIndexOf('|')), ",")) {
            currentPrize.getLaureate().addNation(s);
        }
        for (String s : stringParser(data.substring(data.lastIndexOf('|') + Base.OFFSET), ",")) {
            currentPrize.getLaureate().addLanguage(s);
        }
    }

    private static ArrayList<String> stringParser(String string, String split) {
        return new ArrayList<>(Arrays.asList(string.split(split)));
    }

}
