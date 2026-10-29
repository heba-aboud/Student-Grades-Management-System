public class StudentManager {

    private static final int DEFAULT_CAPACITY = 10;

    private String[] studentIds;
    private String[] studentNames;
    private int[] grades;

    private int studentCount;

    private final ArrayBag<Integer> gradeBag;

    /*
     * categoryCounts[row][0] stores the category number.
     * categoryCounts[row][1] stores the number of students.
     *
     * Category indexes:
     * 0 = Fail
     * 1 = Pass
     * 2 = Good
     * 3 = Very Good
     * 4 = Excellent
     */
    private final int[][] categoryCounts;

    public StudentManager() {
        studentIds = new String[DEFAULT_CAPACITY];
        studentNames = new String[DEFAULT_CAPACITY];
        grades = new int[DEFAULT_CAPACITY];

        studentCount = 0;

        gradeBag = new ArrayBag<>();

        categoryCounts = new int[][] {
                { 0, 0 },
                { 1, 0 },
                { 2, 0 },
                { 3, 0 },
                { 4, 0 }
        };
    }

    /**
     * Adds a new student after validation.
     */
    public boolean addStudent(String id, String name, int grade) {

        if (id == null || id.trim().isEmpty()) {
            return false;
        }

        if (name == null || name.trim().isEmpty()) {
            return false;
        }

        if (grade < 0 || grade > 100) {
            return false;
        }

        id = id.trim();
        name = name.trim();

        // Student IDs must be unique.
        if (studentIdExists(id)) {
            return false;
        }

        ensureStudentCapacity();

        studentIds[studentCount] = id;
        studentNames[studentCount] = name;
        grades[studentCount] = grade;

        // Store the grade in ArrayBag.
        gradeBag.add(grade);

        studentCount++;

        recalculateCategoryCounts();

        return true;
    }

    /**
     * Searches for a student ID.
     *
     * @return the array index, or -1 if not found
     */
    public int findStudentIndex(String id) {

        if (id == null) {
            return -1;
        }

        String searchedId = id.trim();

        for (int index = 0; index < studentCount; index++) {
            if (studentIds[index].equals(searchedId)) {
                return index;
            }
        }

        return -1;
    }

    /**
     * Checks whether a Student ID already exists.
     */
    public boolean studentIdExists(String id) {
        return findStudentIndex(id) != -1;
    }

    /**
     * Returns a Student object from a specific index.
     */
    public Student getStudentAt(int index) {

        if (index < 0 || index >= studentCount) {
            throw new IndexOutOfBoundsException(
                    "Invalid student index: " + index);
        }

        return new Student(
                studentIds[index],
                studentNames[index],
                grades[index]);
    }

    /**
     * Removes a student by Student ID.
     */
    public boolean removeStudent(String id) {

        int studentIndex = findStudentIndex(id);

        if (studentIndex == -1) {
            return false;
        }

        // Remove the student's grade from the Bag.
        int removedGrade = grades[studentIndex];
        gradeBag.remove(removedGrade);

        /*
         * Shift all elements after the removed student
         * one position to the left.
         */
        for (int index = studentIndex; index < studentCount - 1; index++) {
            studentIds[index] = studentIds[index + 1];
            studentNames[index] = studentNames[index + 1];
            grades[index] = grades[index + 1];
        }

        // Clear the last unused position.
        studentIds[studentCount - 1] = null;
        studentNames[studentCount - 1] = null;
        grades[studentCount - 1] = 0;

        studentCount--;

        recalculateCategoryCounts();

        return true;
    }

    /**
     * Returns the total number of students.
     */
    public int getStudentCount() {
        return studentCount;
    }

    /**
     * Calculates the average grade.
     */
    public double calculateAverage() {

        if (studentCount == 0) {
            return 0.0;
        }

        int sum = 0;

        for (int index = 0; index < studentCount; index++) {
            sum += grades[index];
        }

        return (double) sum / studentCount;
    }

    /**
     * Returns the highest grade.
     */
    public int getHighestGrade() {

        if (studentCount == 0) {
            return -1;
        }

        int highest = grades[0];

        for (int index = 1; index < studentCount; index++) {
            if (grades[index] > highest) {
                highest = grades[index];
            }
        }

        return highest;
    }

    /**
     * Returns the lowest grade.
     */
    public int getLowestGrade() {

        if (studentCount == 0) {
            return -1;
        }

        int lowest = grades[0];

        for (int index = 1; index < studentCount; index++) {
            if (grades[index] < lowest) {
                lowest = grades[index];
            }
        }

        return lowest;
    }

    /**
     * Recalculates the number of students in every grade category.
     */
    public void recalculateCategoryCounts() {

        // Reset category counts.
        for (int row = 0; row < categoryCounts.length; row++) {
            categoryCounts[row][1] = 0;
        }

        // Classify every student grade.
        for (int index = 0; index < studentCount; index++) {

            int grade = grades[index];

            if (grade < 60) {
                categoryCounts[0][1]++;
            } else if (grade < 70) {
                categoryCounts[1][1]++;
            } else if (grade < 80) {
                categoryCounts[2][1]++;
            } else if (grade < 90) {
                categoryCounts[3][1]++;
            } else {
                categoryCounts[4][1]++;
            }
        }
    }

    /**
     * Returns a copy of the category-count array.
     */
    public int[][] getCategoryCounts() {

        int[][] copy = new int[categoryCounts.length][2];

        for (int row = 0; row < categoryCounts.length; row++) {
            copy[row][0] = categoryCounts[row][0];
            copy[row][1] = categoryCounts[row][1];
        }

        return copy;
    }

    /**
     * Returns the name of a grade category.
     */
    public String getCategoryName(int categoryIndex) {

        switch (categoryIndex) {
            case 0:
                return "Fail";

            case 1:
                return "Pass";

            case 2:
                return "Good";

            case 3:
                return "Very Good";

            case 4:
                return "Excellent";

            default:
                throw new IllegalArgumentException(
                        "Invalid category index: " + categoryIndex);
        }
    }

    /**
     * Checks whether a grade exists in ArrayBag.
     */
    public boolean gradeExists(int grade) {
        return gradeBag.contains(grade);
    }

    /**
     * Returns the frequency of a grade using ArrayBag.
     */
    public int getGradeFrequency(int grade) {
        return gradeBag.frequencyOf(grade);
    }

    /**
     * Returns the current ArrayBag size.
     */
    public int getGradeBagSize() {
        return gradeBag.size();
    }

    /**
     * Checks whether ArrayBag is empty.
     */
    public boolean isGradeBagEmpty() {
        return gradeBag.isEmpty();
    }

    /**
     * Doubles the arrays' capacity when they become full.
     */
    private void ensureStudentCapacity() {

        if (studentCount == studentIds.length) {

            String[] oldIds = studentIds;
            String[] oldNames = studentNames;
            int[] oldGrades = grades;

            studentIds = new String[oldIds.length * 2];
            studentNames = new String[oldNames.length * 2];
            grades = new int[oldGrades.length * 2];

            for (int index = 0; index < oldIds.length; index++) {
                studentIds[index] = oldIds[index];
                studentNames[index] = oldNames[index];
                grades[index] = oldGrades[index];
            }
        }
    }
}
