import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static Library library;
    private static UserAuthentication auth;
    private static User currentUser;
    private static Scanner scanner;
    
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        library = new Library("books.txt");
        auth = new UserAuthentication("users.txt");
        
        System.out.println("Welcome to the Library Management System");
        
        // Login loop
        while (currentUser == null) {
            currentUser = loginUser();
        }
        
        System.out.println("Login successful. Welcome, " + currentUser.getUsername() + "!");
        
        // Main program loop
        while (true) {
            if (currentUser.getRole() == UserRole.LIBRARIAN) {
                displayLibrarianMenu();
                handleLibrarianChoice();
            } else {
                displayUserMenu();
                handleUserChoice();
            }
        }
    }
    
    private static User loginUser() {
        System.out.println("\n=== Login ===");
        
        try {
            System.out.print("Username: ");
            String username = scanner.nextLine();
            
            System.out.print("Password: ");
            String password = scanner.nextLine();
            
            User user = auth.authenticateUser(username, password);
            
            if (user != null) {
                return user;
            } else {
                System.out.println("Invalid username or password. Please try again.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error during login: " + e.getMessage());
            return null;
        }
    }
    
    private static void displayLibrarianMenu() {
        System.out.print("\n===========================================================================\n");
        System.out.println("\n\t\t\t\tLibrarian Menu\n");
        System.out.println("\t\t1. List All Books");
        System.out.println("\t\t2. Add Book");
        System.out.println("\t\t3. Remove Book");
        System.out.println("\t\t4. Logout");
        System.out.println("\t\t5. Exit");
        System.out.print("\n===========================================================================\n");
        System.out.print("Enter your choice: ");
    }
    
    private static void displayUserMenu() {
        System.out.print("\n===========================================================================\n");
        System.out.println("\n\t\t\t\tUser Menu\n");
        System.out.println("\t\t1. List All Books");
        System.out.println("\t\t2. List Available Books");
        System.out.println("\t\t3. Borrow Book");
        System.out.println("\t\t4. Return Book");
        System.out.println("\t\t5. Logout");
        System.out.println("\t\t6. Exit");
        System.out.print("\n===========================================================================\n");
        System.out.print("Enter your choice: ");
    }
    
    private static int getMenuChoice() {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();
            return choice;
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input! Please enter a number.");
            scanner.nextLine(); // Clear the invalid input
            return -1;
        }
    }
    
    private static void handleLibrarianChoice() {
        int choice = getMenuChoice();
        
        try {
            switch (choice) {
                case 1:
                    library.listBooks();
                    break;
                    
                case 2:
                    addBookProcess();
                    break;
                    
                case 3:
                    removeBookProcess();
                    break;
                    
                case 4:
                    System.out.println("Logging out...");
                    currentUser = null;
                    while (currentUser == null) {
                        currentUser = loginUser();
                    }
                    break;
                    
                case 5:
                    System.out.println("Exiting... Goodbye!");
                    scanner.close();
                    System.exit(0);
                    
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    
    private static void handleUserChoice() {
        int choice = getMenuChoice();
        
        try {
            switch (choice) {
                case 1:
                    library.listBooks();
                    break;
                    
                case 2:
                    library.listAvailableBooks();
                    break;
                    
                case 3:
                    borrowBookProcess();
                    break;
                    
                case 4:
                    returnBookProcess();
                    break;
                    
                case 5:
                    System.out.println("Logging out...");
                    currentUser = null;
                    while (currentUser == null) {
                        currentUser = loginUser();
                    }
                    break;
                    
                case 6:
                    System.out.println("Exiting... Goodbye!");
                    scanner.close();
                    System.exit(0);
                    
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    
    private static void addBookProcess() {
        System.out.print("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n");
        
        try {
            System.out.print("Enter Book Title: ");
            String title = scanner.nextLine();
            
            System.out.print("Enter Author: ");
            String author = scanner.nextLine();
            
            library.addBook(title, author);
        } catch (Exception e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
        
        System.out.print("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n");
    }
    
    private static void removeBookProcess() {
        System.out.print("\n<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");
        
        try {
            System.out.print("Enter Book ID to remove: ");
            if (scanner.hasNextInt()) {
                int removeID = scanner.nextInt();
                scanner.nextLine(); 
                library.removeBook(removeID);
            } else {
                System.out.println("Error: Invalid value entered!");
                System.out.println("Error: Try again using a valid Book ID");
                scanner.nextLine(); 
            }
        } catch (Exception e) {
            System.out.println("Error removing book: " + e.getMessage());
        }
        
        System.out.print("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");
    }
    
    private static void borrowBookProcess() {
        System.out.print("\n<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");
        
        try {
            library.listAvailableBooks();
            
            System.out.print("Enter Book ID to borrow: ");
            if (scanner.hasNextInt()) {
                int bookId = scanner.nextInt();
                scanner.nextLine(); 
                library.borrowBook(bookId);
            } else {
                System.out.println("Error: Invalid value entered!");
                System.out.println("Error: Try again using a valid Book ID");
                scanner.nextLine(); 
            }
        } catch (Exception e) {
            System.out.println("Error borrowing book: " + e.getMessage());
        }
        
        System.out.print("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");
    }
    
    private static void returnBookProcess() {
        System.out.print("\n<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");
        
        try {
            System.out.print("Enter Book ID to return: ");
            if (scanner.hasNextInt()) {
                int bookId = scanner.nextInt();
                scanner.nextLine(); 
                library.returnBook(bookId);
            } else {
                System.out.println("Error: Invalid value entered!");
                System.out.println("Error: Try again using a valid Book ID");
                scanner.nextLine(); 
            }
        } catch (Exception e) {
            System.out.println("Error returning book: " + e.getMessage());
        }
        
        System.out.print("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");
    }
}