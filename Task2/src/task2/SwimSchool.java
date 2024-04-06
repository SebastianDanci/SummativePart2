package task2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author sebid
 */
public class SwimSchool {

    ArrayList<SwimLesson> noviceLessons = new ArrayList<>();
    ArrayList<SwimLesson> improverLessons = new ArrayList<>();
    ArrayList<SwimLesson> advancedLessons = new ArrayList<>();

    public void addMockData() {
        List<String> days = new ArrayList<>(Arrays.asList("Monday", "Wednesday", "Friday"));
        List<String> times = new ArrayList<>(Arrays.asList("17:00", "17:30", "18:00", "18:30", "19:00", "19:30"));
        List<String> levels = new ArrayList<>(Arrays.asList("novice", "improver", "advanced"));
        List<Instructor> instructors = new ArrayList<>(Arrays.asList(
                new Instructor("John"), // for novice
                new Instructor("Maya"), // for improver
                new Instructor("Joseph") // for advanced
        ));

        for (String day : days) {
            for (String time : times) {
                for (int i = 0; i < levels.size(); i++) {
                    SwimLesson lesson = new SwimLesson(day, time, levels.get(i), instructors.get(i));
                    switch (levels.get(i)) {
                        case "novice":
                            noviceLessons.add(lesson);
                            break;
                        case "improver":
                            improverLessons.add(lesson);
                            break;
                        case "advanced":
                            advancedLessons.add(lesson);
                            break;
                    }
                }
            }
        }
        
    }

    public void createStudent() {

    }

}
