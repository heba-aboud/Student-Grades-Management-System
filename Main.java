import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentManager manager = new StudentManager();

    public static void main(String[] args) {

        boolean running = true;

        System.out.println("===============================================");
        System.out.println("     STUDENT GRADES MANAGEMENT SYSTEM");
        System.out.println("===============================================");

        while (running) {

            printMenu();

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    addStudent();
                    break;

                case 2:
                    displayAllStudents();
                    break;

                case 3:
                    searchStudent();
                    break;

                case 4:
                    removeStudent();
                    break;

                case 5:
                    displayStatistics();
                    break;

                case 6:
                    checkGradeUsingArrayBag();
                    break;

                case 7:
                    testArrayBagStatus();
                    break;

                case 8:
                    System.out.println(
                            "Thank you for using the system.");
                    running = false;
                    break;

                default:
                    System.out.println(
                            "Invalid choice. Please choose from 1 to 8.");
            }

            if (running) {
                pressEnterToContinue();
            }
        }

        scanner.close();
    }

    /**
     * Displays the main menu.
     */
    private static void printMenu() {

        System.out.println();
        System.out.println("--------------- MAIN MENU ----------------");
        System.out.println("1. Add a new student");
        System.out.println("2. Display all students");
        System.out.println("3. Search for a student");
        System.out.println("4. Remove a student");
        System.out.println("5. Display statistics");
        System.out.println("6. Check grade existence and frequency");
        System.out.println("7. Display ArrayBag status");
        System.out.println("8. Exit");
        System.out.println("-------------------------------------------");
    }

    /**
     * Adds a new student.
     */
    private static void addStudent() {

        System.out.println();
        System.out.println("---------- ADD STUDENT ----------");

        String id = readNonEmptyString("Enter Student ID: ");
        String name = readNonEmptyString("Enter Student Name: ");
        int grade = readGrade();

        if (manager.studentIdExists(id)) {
            System.out.println(
                    "Error: This Student ID already exists.");
            return;
        }

        boolean added = manager.addStudent(id, name, grade);

        if (added) {
            System.out.println(
                    "Student added successfully.");
        } else {
            System.out.println(
                    "Student could not be added.");
        }
    }

    /**
     * Displays all student records.
     */
    private static void displayAllStudents() {

        System.out.println();
        System.out.println("---------- ALL STUDENTS ----------");

        if (manager.getStudentCount() == 0) {
            System.out.println(
                    "No student records available.");
            return;
        }

        System.out.printf(
                "%-15s %-25s %-10s%n",
                "Student ID",
                "Student Name",
                "Grade");

        System.out.println(
                "--------------------------------------------------");

        for (int index = 0; index < manager.getStudentCount(); index++) {

            Student student = manager.getStudentAt(index);

            System.out.printf(
                    "%-15s %-25s %-10d%n",
                    student.getStudentId(),
                    student.getStudentName(),
                    student.getGrade());
        }
    }

    /**
     * Searches for a student by Student ID.
     */
    private static void searchStudent() {

        System.out.println();
        System.out.println("---------- SEARCH STUDENT ----------");

        String id = readNonEmptyString(
                "Enter Student ID: ");

        int index = manager.findStudentIndex(id);

        if (index == -1) {
            System.out.println(
                    "Student not found.");
            return;
        }

        Student student = manager.getStudentAt(index);

        System.out.println("Student found:");
        System.out.println(
                "Student ID: " + student.getStudentId());
        System.out.println(
                "Student Name: " + student.getStudentName());
        System.out.println(
                "Grade: " + student.getGrade());
    }

    /**
     * Removes a student by Student ID.
     */
    private static void removeStudent() {

        System.out.println();
        System.out.println("---------- REMOVE STUDENT ----------");

        String id = readNonEmptyString(
                "Enter Student ID: ");

        boolean removed = manager.removeStudent(id);

        if (removed) {
            System.out.println(
                    "Student removed successfully.");
        } else {
            System.out.println(
                    "Student not found. No record was removed.");
        }
    }

    /**
     * Displays all statistical information.
     */
    private static void displayStatistics() {

        System.out.println();
        System.out.println("---------- STATISTICS ----------");

        int total = manager.getStudentCount();

        System.out.println(
                "Total students: " + total);

        if (total == 0) {
            System.out.println(
                    "Average grade: N/A");
            System.out.println(
                    "Highest grade: N/A");
            System.out.println(
                    "Lowest grade: N/A");
        } else {
            System.out.printf(
                    "Average grade: %.2f%n",
                    manager.calculateAverage());

            System.out.println(
                    "Highest grade: "
                            + manager.getHighestGrade());

            System.out.println(
                    "Lowest grade: "
                            + manager.getLowestGrade());
        }

        System.out.println();
        System.out.println("Grade categories:");

        int[][] counts = manager.getCategoryCounts();

        for (int row = 0; row < counts.length; row++) {

            System.out.println(
                    manager.getCategoryName(row)
                            + ": "
                            + counts[row][1]
                            + " student(s)");
        }
    }

    /**
     * Tests contains() and frequencyOf() from ArrayBag.
     */
    private static void checkGradeUsingArrayBag() {

        System.out.println();
        System.out.println(
                "---------- ARRAYBAG GRADE CHECK ----------");

        int grade = readGrade();

        boolean exists = manager.gradeExists(grade);
        int frequency = manager.getGradeFrequency(grade);

        System.out.println(
                "Grade " + grade + " exists: " + exists);

        System.out.println(
                "Frequency of grade "
                        + grade
                        + ": "
                        + frequency);
    }

    /**
     * Displays ArrayBag size and empty status.
     */
    private static void testArrayBagStatus() {

        System.out.println();
        System.out.println(
                "---------- ARRAYBAG STATUS ----------");

        System.out.println(
                "ArrayBag size: "
                        + manager.getGradeBagSize());

        System.out.println(
                "ArrayBag is empty: "
                        + manager.isGradeBagEmpty());
    }

    /**
     * Reads a non-empty String from the user.
     */
    private static String readNonEmptyString(String message) {

        while (true) {

            System.out.print(message);

            String value = scanner.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println(
                    "This field cannot be empty.");
        }
    }

    /**
     * Reads a valid grade between 0 and 100.
     */
    private static int readGrade() {

        while (true) {

            int grade = readInt(
                    "Enter grade (0-100): ");

            if (grade >= 0 && grade <= 100) {
                return grade;
            }

            System.out.println(
                    "Invalid grade. "
                            + "Grade must be between 0 and 100.");
        }
    }

    /**
     * Reads an integer safely.
     */
    private static int readInt(String message) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException exception) {

                System.out.println(
                        "Invalid input. "
                                + "Please enter a whole number.");
            }
        }
    }

    /**
     * Pauses the program before displaying the menu again.
     */
    private static void pressEnterToContinue() {

        System.out.println();
        System.out.print(
                "Press Enter to continue...");

        scanner.nextLine();
    }
}