import java.io.*;
import java.util.*;

public class NotesManager {
    private static final String FILE_NAME = "notes.txt";
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Advanced Notes Manager ===");

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add a note");
            System.out.println("2. View all notes");
            System.out.println("3. Search notes");
            System.out.println("4. Delete a specific note");
            System.out.println("5. Clear all notes");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: addNote(); break;
                case 2: viewNotes(); break;
                case 3: searchNotes(); break;
                case 4: deleteNote(); break;
                case 5: clearNotes(); break;
                case 6:
                    System.out.println("Thank you! Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void addNote() {
        System.out.print("Enter your note: ");
        String content = scanner.nextLine();
        Note note = new Note(content);
        try (FileWriter fw = new FileWriter(FILE_NAME, true)) {
            fw.write(note.toString() + "\n");
            System.out.println("Note saved!");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewNotes() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            int count = 1;
            System.out.println("\n--- Your Notes ---");
            while ((line = br.readLine()) != null) {
                System.out.println(count++ + ". " + line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No notes found.");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void searchNotes() {
        System.out.print("Enter keyword to search: ");
        String keyword = scanner.nextLine().toLowerCase();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            boolean found = false;
            System.out.println("\n--- Search Results ---");
            while ((line = br.readLine()) != null) {
                if (line.toLowerCase().contains(keyword)) {
                    System.out.println("- " + line);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("No matching notes found.");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deleteNote() {
        List<String> notes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                notes.add(line);
            }

            if (notes.isEmpty()) {
                System.out.println("No notes to delete.");
                return;
            }

            for (int i = 0; i < notes.size(); i++) {
                System.out.println((i + 1) + ". " + notes.get(i));
            }

            System.out.print("Enter note number to delete: ");
            int num = Integer.parseInt(scanner.nextLine());
            if (num < 1 || num > notes.size()) {
                System.out.println("Invalid number.");
                return;
            }

            notes.remove(num - 1);
            try (FileWriter fw = new FileWriter(FILE_NAME)) {
                for (String note : notes) {
                    fw.write(note + "\n");
                }
            }
            System.out.println("Note deleted.");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void clearNotes() {
        System.out.print("Are you sure you want to delete all notes? (yes/no): ");
        String confirm = scanner.nextLine();
        if (confirm.equalsIgnoreCase("yes")) {
            try (FileWriter fw = new FileWriter(FILE_NAME)) {
                fw.write("");
                System.out.println("All notes cleared.");
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Cancelled.");
        }
    }
}
