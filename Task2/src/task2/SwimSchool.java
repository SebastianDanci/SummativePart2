/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package task2;

import java.util.*;
import java.util.stream.Collectors;
import java.lang.StringBuilder;

/**
 *
 * @author sebid
 */
// Main class for managing swim school operations
public class SwimSchool {

    // Lists to hold different levels of swim lessons
    private static ArrayList<SwimLesson> noviceLessons = new ArrayList<>();
    private static ArrayList<SwimLesson> improverLessons = new ArrayList<>();
    private static ArrayList<SwimLesson> advancedLessons = new ArrayList<>();
    // Scanner for input
    private static Scanner input = new Scanner(System.in);
    // Lists for days, times, and levels for scheduling
    private static List<String> days = new ArrayList<>(Arrays.asList("Monday", "Wednesday", "Friday"));
    private static List<String> times = new ArrayList<>(Arrays.asList("17:00", "17:30", "18:00", "18:30", "19:00", "19:30"));
    private static List<String> levels = new ArrayList<>(Arrays.asList("novice", "improver", "advanced"));
    // List of instructors
    private static List<Instructor> instructors = new ArrayList<>(Arrays.asList(
            new Instructor("John"), // Assigned for novice level
            new Instructor("Maya"), // Assigned for improver level
            new Instructor("Joseph") // Assigned for advanced level
    ));
    // Comparator for sorting students by name alphabetically, ignoring numbers
    private static Comparator<SwimStudent> studentNameComparator = Comparator
            .comparing((SwimStudent swimStudent) -> swimStudent.getName().replaceAll("\\d+", ""), String.CASE_INSENSITIVE_ORDER)
            .thenComparingInt(swimStudent -> extractNumber(swimStudent.getName()));

    // Method to initialize mock data for testing
    public static void addMockData() {
        // Loop through days, times, and levels to create lessons
        for (String day : days) {
            for (String time : times) {
                for (int i = 0; i < levels.size(); i++) {
                    SwimLesson lesson = new SwimLesson(day, time, levels.get(i), instructors.get(i));
                    // Add lesson to the appropriate list based on level
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
        // Simplified example of assigning qualifications to mock students
        Instructor instructor = instructors.get(0); // Using the first instructor for all mock qualifications
        for (int i = 1; i <= 30; i++) {
            ArrayList<Qualification> qualifications = new ArrayList<>();
            String name = "Student" + i;
            SwimLesson assignedLesson;
            String level;
            SwimStudent student;
            // Assign students to levels, lessons, and qualifications
            if (i <= 10) { // Novice students
                level = "novice";
                assignedLesson = noviceLessons.get(i % noviceLessons.size()); // Cycle through available lessons
                // Odd numbers get 5m qualification, even get 10m
                qualifications.add(new DistanceSwim(instructor, null, (i % 2 == 1) ? 5 : 10));
            } else if (i <= 20) { // Improver students
                level = "improver";
                assignedLesson = improverLessons.get((i - 10) % improverLessons.size());
                // All improvers get a 20m qualification, alternating between 100m and 200m
                qualifications.add(new DistanceSwim(instructor, null, 20));
                qualifications.add(new DistanceSwim(instructor, null, ((i - 10) % 2 + 1) * 100));
            } else { // Advanced students
                level = "advanced";
                assignedLesson = advancedLessons.get((i - 20) % advancedLessons.size());
                // Advanced students get both 20m and 400m qualifications, alternating between 800m and 1500m
                qualifications.add(new DistanceSwim(instructor, null, 20));
                qualifications.add(new DistanceSwim(instructor, null, 400));
                qualifications.add(new DistanceSwim(instructor, null, (i % 2 == 0) ? 800 : 1500));
                // Alternating personal survival qualification
                String category = (i % 3 == 0) ? "Gold" : ((i % 3 == 1) ? "Silver" : "Bronze");
                qualifications.add(new PersonalSurvival(instructor, null, category));
            }
            student = new SwimStudent(name, level, qualifications);
            student.setGroup(assignedLesson); // Assign student to the lesson
        }
    }

    private static void awardQualification() {
        ArrayList<SwimStudent> totalStudents = new ArrayList<>();

        List<SwimStudent> allStudents = new ArrayList<>();

        // Consolidating all students from different lesson levels
        for (SwimLesson lesson : noviceLessons) {
            allStudents.addAll(lesson.getParticipants());
        }
        for (SwimLesson lesson : improverLessons) {
            allStudents.addAll(lesson.getParticipants());
        }
        for (SwimLesson lesson : advancedLessons) {
            allStudents.addAll(lesson.getParticipants());
        }

        totalStudents.addAll(allStudents);

        // Prompting for instructor selection
        System.out.println("\n\nSelect an instructor to award qualification: \n");
        instructors.stream()
                .sorted(Comparator.comparing(Instructor::getName))
                .forEach(instructor -> System.out.println((instructors.indexOf(instructor) + 1) + ". " + instructor.getName()));

        int instructorIndex = getIntInput("\nSelect an instructor (or '0' to cancel) > ", 0, instructors.size()) - 1;
        if (instructorIndex == -1) {
            System.out.println("\n\nOperation canceled.\n\n");
            return;
        }
        Instructor selectedInstructor = instructors.get(instructorIndex);

        // Listing students for qualification awarding
        System.out.println("\nSelect a swim student: \n");
        totalStudents.sort(studentNameComparator);
        totalStudents.forEach(student -> System.out.println((student.getID()) + ". " + student.getName() + " - Level: " + student.getLevel() + (student.getWaiting() ? " [Waiting]" : "")));

        int studentId = getIntInput("\n\nPlease enter the id of the desired student (or '0' to cancel) > ");

        if (studentId == 0) {
            System.out.println("\n\nOperation canceled.\n\n");
            return;
        }

        SwimStudent selectedStudent = null;
        for (SwimStudent student : totalStudents) {
            if (student.getID() == studentId) {
                selectedStudent = student;
                break;
            }
        }

        if (selectedStudent == null) {
            System.out.println("\n\nStudent with ID " + studentId + " not found.");
            return;
        }

        // Handling qualification awarding based on student level and group level
        if (selectedStudent.getWaiting()) {
            System.out.println("Can only be awarded " + selectedStudent.getGroup().getLevel() + " qualifications until assigned to a " + selectedStudent.getLevel() + " class.\n\n");
        }

        // Awarding qualifications based on the group level of the student
        if ("advanced".equals(selectedStudent.getGroup().getLevel())) {
            System.out.println("\n1. Distance Swim\n2. Personal Survival");
            int choice = getIntInput("\nSelect qualification type: ", 1, 2);
            if (choice == 1) {
                awardDistanceQualification(selectedStudent, selectedInstructor, Arrays.asList(800, 1500, 3000));
            } else {
                awardPersonalSurvivalQualification(selectedStudent, selectedInstructor);
            }
        } else {
            List<Integer> availableDistances = "novice".equals(selectedStudent.getGroup().getLevel()) ? Arrays.asList(5, 10, 20) : Arrays.asList(100, 200, 400);
            awardDistanceQualification(selectedStudent, selectedInstructor, availableDistances);
        }

        // Check and update student level if they meet the criteria after receiving a new qualification
        checkAndUpgradeStudentLevel(selectedStudent);
    }

    private static void awardDistanceQualification(SwimStudent student, Instructor instructor, List<Integer> distances) {
        // Displaying available distance qualifications for selection
        System.out.println("\n\nSelect qualification to be awarded: \n");
        for (int i = 0; i < distances.size(); i++) {
            System.out.println((i + 1) + ". " + distances.get(i) + "m");
        }
        int distanceIndex = getIntInput("\nSelect a distance qualification (or '0' to cancel) > ", 0, distances.size()) - 1;
        if (distanceIndex == -1) {
            System.out.println("\n\nOperation canceled.\n\n");
            return;
        }
        int selectedDistance = distances.get(distanceIndex);

        // Awarding the qualification if it hasn't been awarded previously
        boolean alreadyAwarded = student.getQualifications().stream()
                .anyMatch(q -> q instanceof DistanceSwim && ((DistanceSwim) q).getDistance() == selectedDistance);
        if (!alreadyAwarded) {
            DistanceSwim newQualification = new DistanceSwim(instructor, student, selectedDistance);
            student.getQualifications().add(newQualification);
            System.out.println("\n" + selectedDistance + "m distance qualification awarded.\n");
        } else {
            System.out.println("Qualification already awarded.");
        }
    }

    private static void awardPersonalSurvivalQualification(SwimStudent student, Instructor instructor) {
        // Defining survival qualification categories
        List<String> categories = Arrays.asList("Bronze", "Silver", "Gold");
        System.out.println("\nSelect a survival qualification (or '0' to cancel) : ");
        // Listing categories for selection
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i));
        }
        // Getting user input for category selection
        int categoryIndex = getIntInput("Select personal survival qualification > ", 0, categories.size()) - 1;
        if (categoryIndex == -1) {
            // Cancelling operation if selected index is '0'
            System.out.println("\n\nOperation canceled.\n\n");
            return;
        }
        String selectedCategory = categories.get(categoryIndex);

        // Checking if the student already has the selected qualification
        boolean alreadyAwarded = student.getQualifications().stream()
                .anyMatch(q -> q instanceof PersonalSurvival && ((PersonalSurvival) q).getCategory().equals(selectedCategory));
        if (!alreadyAwarded) {
            // Awarding new qualification if not already awarded
            PersonalSurvival newQualification = new PersonalSurvival(instructor, student, selectedCategory);
            student.getQualifications().add(newQualification);
            System.out.println("\n\n" + selectedCategory + " personal survival qualification awarded.\n");
        } else {
            // Informing if the qualification was already awarded
            System.out.println("Qualification already awarded.\n\n");
        }
    }

    private static void checkAndUpgradeStudentLevel(SwimStudent student) {
        // Upgrading novice students with a 20m distance swim qualification to improver
        if ("novice".equals(student.getLevel()) && student.getQualifications().stream()
                .anyMatch(q -> q instanceof DistanceSwim && ((DistanceSwim) q).getDistance() == 20)) {
            student.setLevel("improver");
            student.setWaiting(true); // Setting waiting status for class transfer
            WaitingList.addPromoted(student);
            System.out.println(student.getName() + " has been upgraded to improver level. \nStudent added to the waiting list\n\n");
        } else if ("improver".equals(student.getLevel()) && student.getQualifications().stream()
                .anyMatch(q -> q instanceof DistanceSwim && ((DistanceSwim) q).getDistance() == 400)) {
            // Upgrading improver students with a 400m distance swim qualification to advanced
            student.setLevel("advanced");
            student.setWaiting(true); // Setting waiting status for class transfer
            WaitingList.addPromoted(student); // Adding to promoted students list
            System.out.println(student.getName() + " has been upgraded to advanced level. \nStudent added to the waiting list\n\n");
        }
    }

    private static void createStudent() {
        // Prompt for entering new student's name
        System.out.print("\nEnter the name of the new student > ");
        String studentName = input.nextLine().trim(); // Trimming any leading or trailing spaces
        boolean hasAvailableClasses = false;

        // Filtering available novice classes that are not full
        List<SwimLesson> availableNoviceClasses = noviceLessons.stream()
                .filter(lesson -> !lesson.isFull())
                .collect(Collectors.toList());

        // Check if there are any available classes
        hasAvailableClasses = !availableNoviceClasses.isEmpty();

        if (!hasAvailableClasses) {
            // If no available classes, add student to waiting list
            System.out.println("\nNo available classes. Adding " + studentName + " to the waiting list.");
            SwimStudent newStudent = new SwimStudent(studentName);
            newStudent.setWaiting(true);
            WaitingList.addNew(newStudent);
        } else {
            // Display available novice classes
            System.out.println("\n\nAvailable Novice Classes:");
            days.forEach(day -> {
                System.out.println("\n\nDay: " + day + "\n");
                availableNoviceClasses.stream()
                        .filter(lesson -> lesson.getDay().equalsIgnoreCase(day))
                        .forEach(lesson -> System.out.println("  Time: " + lesson.getTime() + " - Spaces Available: " + (SwimLesson.MAX_PARTICIPANTS - lesson.getParticipants().size())));
            });

            // Ask for the day of the week or if to waitlist
            System.out.print("\nEnter the day or 'waitlist' to add the student to the waiting list > ");
            String selectedDay = input.nextLine().trim();

            // Option to add to waitlist directly
            if ("waitlist".equalsIgnoreCase(selectedDay)) {
                System.out.println("\n" + "\n" + studentName + " has been added to the waiting list.\n\n");
                SwimStudent newStudent = new SwimStudent(studentName);
                newStudent.setWaiting(true);
                WaitingList.addNew(newStudent);
                return;
            }

            // Validate the selected day
            if (!days.contains(capitalizeFirstLetter(selectedDay.toLowerCase()))) {
                System.out.println("\n\nInvalid day selected. Please try again.\n\n");
                return; // Exit if invalid day is selected
            }

            // Ask for the class time or option to add to waitlist
            System.out.print("\nEnter the time for " + capitalizeFirstLetter(selectedDay.toLowerCase()) + " or 'waitlist' to add the student to the waiting list > ");
            String selectedTime = input.nextLine().trim();

            // Option to add to waitlist directly
            if ("waitlist".equalsIgnoreCase(selectedTime)) {
                System.out.println("\n" + "\n" + studentName + " has been added to the waiting list.\n\n");
                SwimStudent newStudent = new SwimStudent(studentName);
                newStudent.setWaiting(true);
                WaitingList.addNew(newStudent);
                return;
            }

            // Find the lesson based on day and time
            SwimLesson selectedLesson = availableNoviceClasses.stream()
                    .filter(lesson -> lesson.getDay().equalsIgnoreCase(capitalizeFirstLetter(selectedDay.toLowerCase())) && lesson.getTime().equalsIgnoreCase(selectedTime))
                    .findFirst()
                    .orElse(null);

            if (selectedLesson != null) {
                // Add student to the selected lesson if found
                SwimStudent newStudent = new SwimStudent(studentName, selectedLesson);
                System.out.println("\n" + "\n" + studentName + " has been successfully added to the class on " + capitalizeFirstLetter(selectedDay.toLowerCase()) + " at " + selectedTime + ".\n\n");
            } else {
                // Inform user if the selected time is not available
                System.out.println("\n" + "\n" + "Selected time is not available. Please try again.\n\n");
            }
        }
    }

// Helper method to capitalize the first letter of a string
    private static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input; // Return original input if it's null or empty
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1); // Capitalize first letter
    }

    // Displays the main menu options to the user
    private static void printMenuOptions() {
        final int CONTENTWIDTH = 42; // Width for the separator

        // Print the menu header and options
        printSeparator(CONTENTWIDTH); // Prints a separator line
        System.out.println("             Swim School Menu"); // Menu title
        printSeparator(CONTENTWIDTH); // Prints a separator line again for styling
        // List of menu options
        System.out.println("View swim student information .......... 1");
        System.out.println("View swim lesson details ............... 2");
        System.out.println("View instructor schedule ............... 3");
        System.out.println("Add new swim student ................... 4");
        System.out.println("Award swim qualification ............... 5");
        System.out.println("Move swim student from waiting list .... 6");
        System.out.println("Exit ................................... 0");
        printSeparator(CONTENTWIDTH); // Prints a final separator line
        System.out.print("Enter choice > "); // Prompts user for menu option choice
    }

// Displays information about all registered swim students
    private static void viewInformation() {
        List<SwimStudent> allStudents = new ArrayList<>(); // Initialize a new list to hold all students

        // Collect students from each level of lessons into one list
        for (SwimLesson lesson : noviceLessons) { // For each novice lesson
            allStudents.addAll(lesson.getParticipants()); // Add its participants
        }
        for (SwimLesson lesson : improverLessons) { // For each improver lesson
            allStudents.addAll(lesson.getParticipants()); // Add its participants
        }
        for (SwimLesson lesson : advancedLessons) { // For each advanced lesson
            allStudents.addAll(lesson.getParticipants()); // Add its participants
        }

        allStudents.addAll(WaitingList.getNew()); // Include students on the waiting list

        // Sorts students alphabetically by their name, addressing numbers within names correctly
        allStudents.sort(studentNameComparator);

        // Prints student information or a message if no students are registered
        System.out.println("Students of this Swimming School (ID's): \n");
        if (allStudents.isEmpty()) { // Check if the list is empty
            System.out.println("\nThere are no students registered in the school!\n\n"); // Inform the user
        } else { // If not empty
            // Display each student's ID, name, and level
            allStudents.forEach(student -> System.out.printf("%d. %s, Level - %s\n", student.getID(), student.getName(), student.getLevel()));
        }

        // Prompts the user to select a student for more information
        int studentId = getIntInput("\n\nPlease enter the id of the desired student (or '0' to cancel) > ");

        // Cancel operation if 0 is entered
        if (studentId == 0) {
            System.out.println("\n\nOperation canceled.\n\n");
            return;
        }

        // Find and display information for the selected student
        SwimStudent selectedStudent = null; // Initialize selected student as null
        for (SwimStudent student : allStudents) { // Search through all students
            if (student.getID() == studentId) { // If a match is found
                selectedStudent = student; // Set as the selected student
                break; // Break the loop as the student is found
            }
        }

        // Display the selected student's details or indicate if not found
        if (selectedStudent != null) { // If a student was selected
            System.out.println(selectedStudent.toString()); // Print their details
        } else { // If no match was found
            System.out.println("\n\nStudent with ID " + studentId + " not found."); // Inform the user
        }
    }

    // Extracts and returns the numerical part from a string
    private static int extractNumber(String str) {
        String number = str.replaceAll("\\D+", ""); // Remove all non-digit characters from the string
        return number.isEmpty() ? 0 : Integer.parseInt(number); // Convert the string to an integer, defaulting to 0 if empty
    }

// Allows the user to view details of a specific swim lesson
    private static void viewLessonDetails() {
        System.out.println("\n\nSelect a swim lesson to view details:");

        // Prompt user to choose day, time, and level
        String selectedDay = chooseOption("\n\nSelect a day:\n", days);
        if (selectedDay.equals("0")) {
            System.out.println("\n\nOperation canceled.\n\n");
            return;
        }
        String selectedTime = chooseOption("\n\nSelect a start time:\n", times);
        if (selectedTime.equals("0")) {
            System.out.println("\n\nOperation canceled.\n\n");
            return;
        }
        String selectedLevel = chooseOption("\n\nSelect a level:\n", levels);
        if (selectedLevel.equals("0")) {
            System.out.println("\n\nOperation canceled.\n\n");
            return;
        }

        // Find the lesson that matches the selected day, time, and level
        SwimLesson selectedLesson = null;
        for (SwimLesson lesson : noviceLessons) {
            if (lesson.getDay().equals(selectedDay) && lesson.getTime().equals(selectedTime) && lesson.getLevel().equals(selectedLevel)) {
                selectedLesson = lesson;
                break;
            }
        }
        if (selectedLesson == null) {
            for (SwimLesson lesson : improverLessons) {
                if (lesson.getDay().equals(selectedDay) && lesson.getTime().equals(selectedTime) && lesson.getLevel().equals(selectedLevel)) {
                    selectedLesson = lesson;
                    break;
                }
            }
        }
        if (selectedLesson == null) {
            for (SwimLesson lesson : advancedLessons) {
                if (lesson.getDay().equals(selectedDay) && lesson.getTime().equals(selectedTime) && lesson.getLevel().equals(selectedLevel)) {
                    selectedLesson = lesson;
                    break;
                }
            }
        }

        // Display details of the selected lesson
        if (selectedLesson != null) {
            System.out.println("\n\n\nDetails of the selected lesson:\n");
            System.out.println(selectedLesson.toString());
        } else {
            System.out.println("\n\nLesson not found. \n");
        }
    }

    // Prompts the user with a list of options and returns the user's selection or allows cancellation
    private static String chooseOption(String prompt, List<String> options) {
        System.out.println(prompt);
        int count = 1;
        // Display each option with a number
        for (String option : options) {
            System.out.println(count + ". " + option);
            count++;
        }
        // Get user input with the option to cancel
        int choice = getIntInput("\nEnter the number of your choice (or '0' to cancel) > ", 0, options.size());
        if (choice == 0) { // Return '0' if the user chooses to cancel
            return "0";
        }
        // Return the selected option based on user input
        return options.get(choice - 1);
    }

// Displays the schedule for a selected instructor
    private static void viewInstructorSchedule() {
        // Sort instructors alphabetically for display
        List<Instructor> sortedInstructors = new ArrayList<>(instructors);
        sortedInstructors.sort(Comparator.comparing(Instructor::getName));

        // Prompt user to select an instructor from the list
        System.out.println("Select an instructor to view schedule:\n");
        for (int i = 0; i < sortedInstructors.size(); i++) {
            System.out.println((i + 1) + ". " + sortedInstructors.get(i).getName());
        }

        // Get user input with an option to cancel
        int choice = getIntInput("\nSelect an instructor (or '0' to cancel) > ", 0, sortedInstructors.size());
        if (choice == 0) { // Exit if user cancels
            System.out.println("\n\nOperation canceled.\n\n");
            return;
        }
        Instructor selectedInstructor = sortedInstructors.get(choice - 1);

        // Retrieve and sort lessons taught by the selected instructor by day and start time
        List<SwimLesson> lessons = selectedInstructor.getLessonsTeached();
        lessons.sort(Comparator.comparing(SwimLesson::getDay).thenComparing(SwimLesson::getTime));

        // Display the sorted schedule for the selected instructor
        System.out.println("\n\n\n" + selectedInstructor.getName() + "'s Schedule:");
        lessons.forEach(lesson -> {
            // For each lesson, display day, time, level, participating students, and available spaces
            System.out.println("\nDay: " + lesson.getDay() + ", Time: " + lesson.getTime() + ", Level: " + lesson.getLevel());
            if (lesson.getParticipants().isEmpty()) {
                System.out.println("  No students currently allocated.");
            } else {
                System.out.println("  Students allocated: ");
                lesson.getParticipants().forEach(student -> System.out.println("    - " + student.getName()));
            }
            // Calculate and display available spaces in the class
            int spacesAvailable = SwimLesson.MAX_PARTICIPANTS - lesson.getParticipants().size();
            System.out.println("  Spaces Available: " + spacesAvailable);
        });
        System.out.println("\n");
    }

    // Moves a student from the waiting list to an available class
    private static void moveStudent() {
        // Check if there are any students on the waiting list
        if (WaitingList.getNew().isEmpty() && WaitingList.getPromoted().isEmpty()) {
            System.out.println("\nThere are no students on the waitlist!\n");
            return; // Exit if no students are waiting
        }

        // Combine new and promoted students on the waiting list
        List<SwimStudent> waitingStudents = new ArrayList<>();
        waitingStudents.addAll(WaitingList.getNew());
        waitingStudents.addAll(WaitingList.getPromoted());

        // Display waiting students for selection
        System.out.println("\n\nSelect a student to move:\n");
        for (int i = 0; i < waitingStudents.size(); i++) {
            SwimStudent student = waitingStudents.get(i);
            // Show student ID, name, and level with waiting status
            System.out.println(student.getID() + ". " + student.getName() + "   -   Waiting for assignment to a class with level: " + student.getLevel() + (student.getWaiting() ? "   -   [Waiting]" : ""));
        }

        // Prompt user to select a student by ID
        int studentId = getIntInput("\n\nEnter the id of the desired  (or '0' to cancel) > ");
        if (studentId == 0) {
            System.out.println("\n\nOperation canceled.\n\n"); // Exit if user cancels
            return;
        }

        // Find the selected student from the list
        SwimStudent selectedStudent = null;
        for (SwimStudent student : waitingStudents) {
            if (student.getID() == studentId) {
                selectedStudent = student;
                break; // Break loop once student is found
            }
        }

        if (selectedStudent == null) { // Check if student was found
            System.out.println("\n\nStudent with ID " + studentId + " not found.");
            return;
        }

        // Find available lessons for the student's level
        List<SwimLesson> availableLessons = getAvailableLessonsForStudentLevel(selectedStudent.getLevel());
        if (availableLessons.isEmpty()) { // Check if there are available lessons
            System.out.println("No available classes for " + selectedStudent.getName() + " at the " + selectedStudent.getLevel() + " level.");
            return;
        }

        // Display available classes for the selected student
        System.out.println("\n\nSelect a class to move the student to:");
        String day = "";
        for (int i = 0; i < availableLessons.size(); i++) {
            SwimLesson lesson = availableLessons.get(i);
            if (!day.equals(lesson.getDay())) { // Group lessons by day
                System.out.println("\n\n" + lesson.getDay() + ": \n");
                day = lesson.getDay();
            }
            // Show lesson time, level, and availability
            System.out.println((i + 1) + ". " + lesson.getTime() + " - " + lesson.getLevel() + " level" + (lesson.isFull() ? " [Full]" : ""));
        }

        // Prompt user to choose a class
        int lessonChoice = getIntInput("\nSelect a time for the class (or '0' to cancel) > ", 0, availableLessons.size());
        if (lessonChoice == 0) {
            System.out.println("\n\nOperation canceled.\n\n"); // Exit if user cancels
            return;
        }
        SwimLesson selectedLesson = availableLessons.get(lessonChoice - 1);

        // Move student to the selected class and update their status
        if (selectedStudent.getGroup() != null) {
            // Remove student from current class if they are already enrolled in one
            selectedStudent.getGroup().removeParticipant(selectedStudent.getID());
        }
        selectedStudent.setGroup(selectedLesson); // Assign student to the new class
        removeFromWaitlist(selectedStudent); // Remove student from the waiting list
        System.out.println("\n" + selectedStudent.getName() + " has been moved to the " + selectedLesson.getDay() + " at " + selectedLesson.getTime() + " class.\n\n");
    }

// Returns a list of available lessons for a specific level
    private static List<SwimLesson> getAvailableLessonsForStudentLevel(String level) {
        // Filter lessons by level and availability
        switch (level) {
            case "novice":
                return noviceLessons.stream().filter(l -> !l.isFull()).collect(Collectors.toList());
            case "improver":
                return improverLessons.stream().filter(l -> !l.isFull()).collect(Collectors.toList());
            case "advanced":
                return advancedLessons.stream().filter(l -> !l.isFull()).collect(Collectors.toList());
            default:
                return new ArrayList<>(); // Return empty list for unrecognized levels
        }
    }

// Removes a student from the waiting list and updates their waiting status
    private static void removeFromWaitlist(SwimStudent student) {
        student.setWaiting(false); // Set waiting status to false
        WaitingList.removeStudentById(student.getID()); // Remove student by ID from the waiting list
    }

// Prints a separator line of a given length
    public static void printSeparator(int contentWidth) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i <= contentWidth; i++) {
            str.append("-"); // Append "-" to the string builder for each unit of content width
        }
        System.out.println(str); // Print the constructed separator line
    }

// Requests integer input from the user with a prompt
    private static int getIntInput(String prompt) {
        int num = 0; // Initialize number to return
        boolean validInput = false; // Flag to check for valid input
        while (!validInput) {
            try {
                System.out.print(prompt); // Display the prompt
                num = Integer.parseInt(input.nextLine()); // Parse input to integer
                validInput = true; // Input is valid, exit loop
            } catch (NumberFormatException e) {
                System.out.print("\n\nInvalid input. Please enter a valid number."); // Handle invalid numeric input
            }
        }
        return num; // Return the parsed number
    }

// Requests integer input from the user within a specified range
    private static int getIntInput(String prompt, int min, int max) {
        int number = 0; // Initialize number to return
        boolean valid = false; // Flag to check for valid input
        while (!valid) {
            System.out.print(prompt); // Display the prompt
            try {
                number = Integer.parseInt(input.nextLine()); // Parse input to integer
                if (number >= min && number <= max) { // Check if number is within range
                    valid = true; // Input is valid and within range, exit loop
                } else {
                    System.out.println("Please enter a number between " + min + " and " + max + "."); // Prompt for input within range
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number."); // Handle invalid numeric input
            }
        }
        return number; // Return the parsed number
    }

// Main loop for running the menu-driven application
    public static void runMenu() {
        String choice = "1"; // Initialize choice for menu selection
        while (!choice.equals("0")) { // Continue until "0" is chosen to exit
            printMenuOptions(); // Display menu options
            choice = input.nextLine(); // Get user choice
            switch (choice) { // Handle choice with appropriate method
                case "1":
                    viewInformation(); // View swim student information
                    break;
                case "2":
                    viewLessonDetails(); // View details of a swim lesson
                    break;
                case "3":
                    viewInstructorSchedule(); // View an instructor's schedule
                    break;
                case "4":
                    createStudent(); // Add a new swim student
                    break;
                case "5":
                    awardQualification(); // Award a qualification to a student
                    break;
                case "6":
                    moveStudent(); // Move a student from waiting list to a class
                    break;
                case "0":
                    System.out.println("\n\nExiting...\n"); // Exit the application
                    break;
                default:
                    System.out.println("Invalid choice"); // Handle invalid menu choice
                    break;
            }
        }
        input.close(); // Close scanner to prevent resource leak
    }
}
