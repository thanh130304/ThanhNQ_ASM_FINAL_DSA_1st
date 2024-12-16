import java.sql.SQLOutput;
import java.util.Random;
import java.util.Scanner;

public class StudentManager {
    private StudentStack studentStack;

    // Constructor
    public StudentManager() {
        studentStack = new StudentStack(10000); // Max 10,000 students
    }

    // Generate random students
    private void generateRandomStudents(Scanner scanner) {
        Random random = new Random();
        System.out.print("Enter the number of students to generate: ");
        int numberOfStudents = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < numberOfStudents; i++) {
            String id = "ID" + (i + 1);
            String name = "Student" + (i + 1);
            double marks = random.nextDouble() * 10; // Random marks between 0 and 10
            studentStack.push(new Student(id, name, marks));
        }
        System.out.println("Generated " + numberOfStudents + " random students!");
    }

    // Display all students
    private void displayAllStudents() {
        Student[] students = studentStack.getAll();

        // Check if there are no students in the stack
        if (students.length == 0) {
            System.out.println("No students found.");
        } else {
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }


    // Add a new student
    private void addStudent(Scanner scanner) {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Student Marks: ");
        double marks = scanner.nextDouble();
        scanner.nextLine();

        studentStack.push(new Student(id, name, marks));
        System.out.println("Student added successfully!");
    }

    // Edit an existing student
    private void editStudent(Scanner scanner) {
        System.out.print("Enter Student ID to edit: ");
        String id = scanner.nextLine();

        Student[] students = studentStack.getAll();
        for (Student student : students) {
            if (student.getId().equals(id)) {
                System.out.print("Enter new name: ");
                String name = scanner.nextLine();
                System.out.print("Enter new marks: ");
                double marks = scanner.nextDouble();
                scanner.nextLine();
                // Create new student with updated information
                studentStack.push(new Student(id, name, marks));
                System.out.println("Student information updated successfully!");
                return;
            }
        }
        System.out.println("Student with ID " + id + " not found!");
    }

    // Delete a student
    private void deleteStudent(Scanner scanner) {
        System.out.print("Enter Student ID to delete: ");
        String id = scanner.nextLine();

        Student[] students = studentStack.getAll();
        for (int i = 0; i < students.length; i++) {
            if (students[i].getId().equals(id)) {
                System.out.println("Deleting student: " + students[i]);
                // Remove student from stack
                students[i] = null;
                System.out.println("Student deleted successfully!");
                return;
            }
        }
        System.out.println("Student with ID " + id + " not found!");
    }

    // Sort students by marks (QuickSort or BubbleSort)
    private void sortStudents(Scanner scanner) {
        if (studentStack.size() == 0) {
            System.out.println("No students found.");
            return; // Exit the method if no students are available to sort
        }

        System.out.println("1. Quick Sort");
        System.out.println("2. Bubble Sort");
        System.out.print("Choose sorting method: ");
        int choice = scanner.nextInt();
        long startTime, endTime;

        if (choice == 1) {
            startTime = System.nanoTime();
            studentStack.sortStudentsByMarksQuick(0, studentStack.size() - 1);
            endTime = System.nanoTime();
            System.out.println("Students sorted using Quick Sort.");
        } else if (choice == 2) {
            startTime = System.nanoTime();
            studentStack.sortStudentsByMarksBubble();
            endTime = System.nanoTime();
            System.out.println("Students sorted using Bubble Sort.");
        } else {
            System.out.println("Invalid choice.");
            return; // Exit method if invalid choice
        }

        displayAllStudents(); // Display sorted students

        // Display execution time after displaying the sorted list
        System.out.println("Execution Time: " + (endTime - startTime) + " nanoseconds");
    }

    // Checking space usage
    private void checkSpaceUsage(Scanner scanner) {
        int capacity = studentStack.getCapacity(); // Max size of stack
        int used = studentStack.size(); // Current number of students
        int free = capacity - used;

        System.out.println("Stack Capacity: " + capacity);
        System.out.println("Used Slots: " + used);
        System.out.println("Free Slots: " + free);
    }


    // Search a student by ID
    private void searchStudentById(Scanner scanner) {
        System.out.print("Enter Student ID to search: ");
        String id = scanner.nextLine();

        Student[] students = studentStack.getAll();
        for (Student student : students) {
            if (student.getId().equals(id)) {
                System.out.println("Found: " + student);
                return;
            }
        }
        System.out.println("Student with ID " + id + " not found!");
    }

    // Adaptive sorting based on dataset size
    private void adaptiveSortStudents(Scanner scanner) {
        long startTime, endTime;
        int numberOfStudents = studentStack.size();

        if (numberOfStudents == 0) {
            System.out.println("No students to sort.");
            return;
        }

        String sortingAlgorithmUsed;

        // Measure execution time
        if (numberOfStudents <= 100) {
            startTime = System.nanoTime();
            studentStack.sortStudentsByMarksBubble();
            endTime = System.nanoTime();
            sortingAlgorithmUsed = "Used Bubble Sort for small dataset.";
        } else {
            startTime = System.nanoTime();
            studentStack.sortStudentsByMarksQuick(0, numberOfStudents - 1);
            endTime = System.nanoTime();
            sortingAlgorithmUsed = "Used Quick Sort for large dataset.";
        }

        // Display the sorted list
        displayAllStudents();

        // Display sorting details
        System.out.println("\nSorting Details:");
        System.out.println("Number of Students Sorted: " + numberOfStudents);
        System.out.println(sortingAlgorithmUsed);  // Display sorting algorithm used here
        System.out.println("Execution Time: " + (endTime - startTime) + " nanoseconds");
    }


    // Run the program
    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Generate Random Students");
            System.out.println("2. Display All Students");
            System.out.println("3. Add Student");
            System.out.println("4. Edit Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Sort Students By Marks");
            System.out.println("    1. Quick Sort");
            System.out.println("    2. Bubble Sort");
            System.out.println("7. Search Student By ID");
            System.out.println("8. Check Space Usage");
            System.out.println("9. Adaptive Sort Students");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    generateRandomStudents(scanner);
                    break;
                case 2:
                    displayAllStudents();
                    break;
                case 3:
                    addStudent(scanner);
                    break;
                case 4:
                    editStudent(scanner);
                    break;
                case 5:
                    deleteStudent(scanner);
                    break;
                case 6:
                    sortStudents(scanner);
                    break;
                case 7:
                    searchStudentById(scanner);
                    break;
                case 8:
                    checkSpaceUsage(scanner);
                    break;
                case 9:
                    adaptiveSortStudents(scanner);
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        StudentManager manager = new StudentManager();
        manager.run();
    }
}
