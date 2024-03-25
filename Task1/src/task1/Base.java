/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package task1;

import java.util.ArrayList;
import java.lang.StringBuilder;
import java.util.Scanner;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author sebid
 */
public class Base {

    private static ArrayList<LiteraturePrize> prizes = new ArrayList<LiteraturePrize>();
    private static Scanner input = new Scanner(System.in);
    private static int firstPrize, lastPrize = -1;
    public static final String HEADER = "| Winner(s)                 | Born | Died | Language(s)           | Genre(s)             |";
    public static final byte OFFSET = 1;

    public static void addPrize(LiteraturePrize prize) {
        if (lastPrize == -1) {
            firstPrize = prize.getYear();
        }
        if (prize.getYear() > lastPrize) {
            lastPrize = prize.getYear();
        }
        if (prize.getYear() < firstPrize) {
            firstPrize = prize.getYear();
        }
        prizes.add(prize);
    }

    public static ArrayList<LiteraturePrize> getPrizes() {
        return prizes;
    }

    private static void printOptions() {
        final int CONTENTWIDTH = 21;

        printSeparator(CONTENTWIDTH);
        System.out.println("Literature prize menu");
        printSeparator(CONTENTWIDTH);
        System.out.println("List ...............1");
        System.out.println("Select .............2");
        System.out.println("Search .............3");
        System.out.println("Exit ...............0");
        printSeparator(CONTENTWIDTH);
        System.out.print("Enter choice > ");
    }

    private static void menuList() {
        final String COLUMNHEADER = "| Year | Prize Winners (and asociated nations)";
        int start, end, contentWidth = 0;
        final int PADDING = 7;

        start = readYear("\nEnter start year > ") - prizes.get(0).getYear();
        end = readYear("\nEnter end year > ") - prizes.get(0).getYear();
        if (start > end) {
            end = (start + end) - (start = end);
        }
        for (int i = start; i <= end; i++) {
            int size = prizes.get(i).toString().length();
            if (contentWidth < size) {
                contentWidth = size;
            }
        }
        if (contentWidth < COLUMNHEADER.length()) {
            contentWidth = COLUMNHEADER.length();
        }
        contentWidth += PADDING;
        StringBuilder str = new StringBuilder();
        printSeparator(contentWidth);
        str.setLength(0);
        str.append(COLUMNHEADER);
        addPadding(str, contentWidth - COLUMNHEADER.length());
        System.out.println(str);
        str.setLength(0);
        printSeparator(contentWidth);
        for (int i = start; i <= end; i++) {
            str.setLength(0);
            str.append(prizes.get(i).toString());
            addPadding(str, contentWidth - prizes.get(i).toString().length());
            System.out.println(str.toString());
        }
        str.setLength(0);
        printSeparator(contentWidth);
    }

    private static void menuSelect() {
        final int CHOICE = readYear("\nEnter year > ") - prizes.get(0).getYear();
        if (prizes.get(CHOICE).getWinners().size() == 0) {
            System.out.println("NO PRIZE AWARDED THIS YEAR\n\n");
            return;
        }
        printSeparator(HEADER.length() - OFFSET);
        System.out.println(HEADER);
        printSeparator(HEADER.length() - OFFSET);
        for (Laureate l : prizes.get(CHOICE).getWinners()) {
            System.out.println(l.toString());
            printSeparator(HEADER.length() - OFFSET);
        }
        System.out.println("\n");
    }

    private static void menuSearch() {
        final String NAME = "| Name", GENRES = "Genres", YEAR = "Year";
        final int PADDING = 3;
        int longestLength = 0, longestName = 0;
        System.out.print("\nEnter search term for writing genre > ");
        ArrayList<Laureate> matches = new ArrayList<Laureate>();
        ArrayList<Integer> years = new ArrayList<Integer>();
        String search = input.nextLine().toLowerCase();
        for (LiteraturePrize p : prizes) {
            for (Laureate l : p.getWinners()) {
                int currentLength = 0;
                boolean hasMatch = false;
                for (String s : l.getGenre()) {
                    currentLength += s.length() + 1;
                    if (s.contains(search)) {
                        matches.add(l);
                        years.add(p.getYear());
                        hasMatch = true;
                    }
                }
                if (currentLength > longestLength && hasMatch) {
                    longestLength = currentLength;
                }
                if (hasMatch && longestName < l.getName().length()) {
                    longestName = l.getName().length();
                }
            }
        }
        if (matches.size() == 0) {
            System.out.println("\nThere are no matches found for the given term :'" + search + "'\n");
            return;
        }
        List<Integer> indices = new ArrayList<Integer>();
        for (int i = 0; i < matches.size(); i++) {
            indices.add(i);
        }

        Collections.sort(indices, new Comparator<Integer>() {
            public int compare(Integer i1, Integer i2) {
                return matches.get(i1).getName().compareTo(matches.get(i2).getName());
            }
        });

        List<Laureate> sortedMatches = new ArrayList<Laureate>(matches.size());
        List<Integer> sortedYears = new ArrayList<Integer>(years.size());
        for (int index : indices) {
            sortedMatches.add(matches.get(index));
            sortedYears.add(years.get(index));
        }

        matches.clear();
        matches.addAll(sortedMatches);
        years.clear();
        years.addAll(sortedYears);

        if (longestLength < GENRES.length()) {
            longestLength = GENRES.length();
        }

        StringBuilder str = new StringBuilder();
        printSeparator(str, longestLength + longestName + OFFSET * 9 + PADDING * 2 + YEAR.length());
        str.append(NAME);
        addPadding(str, longestName + PADDING - NAME.length() + OFFSET * 2);
        str.append(" " + GENRES);
        addPadding(str, longestLength + PADDING - GENRES.length() + OFFSET);
        str.append(" " + YEAR);
        addPadding(str, OFFSET);
        printSeparator(str, longestLength + longestName + OFFSET * 9 + PADDING * 2 + YEAR.length());

        for (int i = 0; i < matches.size(); i++) {
            str.append("| " + matches.get(i).getName());
            addPadding(str, PADDING + longestName - matches.get(i).getName().length());
            str.append(" ");
            int currentLength = 0;
            for (String s : matches.get(i).getGenre()) {
                if (s.contains(search)) {
                    str.append(s.substring(0, s.indexOf(search)));
                    str.append(s.substring(s.indexOf(search), s.indexOf(search) + search.length()).toUpperCase());
                    str.append(s.substring(s.indexOf(search) + search.length()) + ",");
                } else {
                    str.append(s + ",");
                }
                currentLength += s.length() + 1;
            }
            str.deleteCharAt(str.length() - 1);
            addPadding(str, PADDING + longestLength - currentLength + OFFSET * 2);
            addPadding(str.append(" " + years.get(i)), OFFSET);
            printSeparator(str, longestLength + longestName + OFFSET * 9 + PADDING * 2 + YEAR.length());
        }

        System.out.println(str);

    }

    public static int readYear(String message) {
        int integer = -1;

        while (true) {
            try {
                System.out.print(message);
                integer = Integer.parseInt(input.nextLine().trim());
                if (integer < prizes.get(0).getYear() || integer > prizes.get(prizes.size() - OFFSET).getYear()) {
                    throw new ArithmeticException("\n\nError: Year not in database;\nTry a number between: " + prizes.get(0).getYear() + " - " + prizes.get(prizes.size() - OFFSET).getYear());
                }
                break;
            } catch (ArithmeticException iae) {
                System.out.println(iae.getMessage());
            } catch (Exception e) {
                System.out.println("\n\nError: Expected integer input;");
            }
        }
        return integer;
    }

    public static void runMenu() {
        String choice = "1";

        while (!choice.equals("0")) {
            printOptions();
            choice = input.nextLine();
            switch (choice) {
                case "1":
                    menuList();
                    break;
                case "2":
                    menuSelect();
                    break;
                case "3":
                    menuSearch();
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }

    public static void sortArray() {
        Collections.sort(prizes, new Comparator<LiteraturePrize>() {
            public int compare(LiteraturePrize p1, LiteraturePrize p2) {
                return Integer.compare(p1.getYear(), p2.getYear());
            }
        });
    }

    public static void printSeparator(int contentWidth) {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i <= contentWidth; i++) {
            str.append("-");
        }
        System.out.println(str);
    }

    public static StringBuilder printSeparator(StringBuilder str) {
        str.append("\n");
        for (int i = 0; i <= Base.HEADER.length() - 1; i++) {
            str.append("-");
        }
        str.append("\n");
        return str;
    }

    public static StringBuilder printSeparator(StringBuilder str, int length) {
        str.append("\n");
        for (int i = 0; i <= length - 1; i++) {
            str.append("-");
        }
        str.append("\n");
        return str;
    }

    public static void addPadding(StringBuilder str, int padding) {
        for (int i = 0; i < padding; i++) {
            str.append(" ");
        }
        str.append("|");
    }

    public static void printCitation(StringBuilder str, String citation) {
        final int boxWidth = HEADER.length();
        final int innerWidth = boxWidth - 20;
        final String topBottomBorder = repeat("-", boxWidth);
        final String emptyLine = "|" + repeat(" ", boxWidth - 2) + "|";

        String centeredCitationTitle = centerTextInBox("Citation:", boxWidth, 10);
        str.append(centeredCitationTitle).append("\n");

        str.append(emptyLine).append("\n");

        String[] words = citation.split(" ");
        StringBuilder line = new StringBuilder();
        for (String word : words) {
            if (line.length() + word.length() + 1 <= innerWidth) {
                if (line.length() > 0) {
                    line.append(" ");
                }
                line.append(word);
            } else {
                str.append(centerTextInBox(line.toString(), boxWidth, 10)).append("\n");
                line = new StringBuilder(word);
            }
        }
        if (line.length() > 0) {
            str.append(centerTextInBox(line.toString(), boxWidth, 10)).append("\n");
        }

        str.append(emptyLine);

    }

    private static String centerTextInBox(String text, int boxWidth, int paddingPerSide) {
        int textPadding = (boxWidth - 2 * paddingPerSide - text.length()) / 2;
        String padding = repeat(" ", Math.max(textPadding + paddingPerSide, paddingPerSide));
        return "|" + padding + text + repeat(" ", boxWidth - 2 - padding.length() - text.length()) + "|";
    }

    public static String repeat(String str, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
}
