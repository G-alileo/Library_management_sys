import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("\n===========================================================================\n");
            System.out.println("\n\t\t\t\tLibrary Management System\n");
            System.out.println("\t\t1. List Books");
            System.out.println("\t\t2. Add Book");
            System.out.println("\t\t3. Remove Book");
            System.out.println("\t\t4. Exit");
            System.out.print("\n===========================================================================\n");
            System.out.print("Enter your choice: ");

            int choice = 4;
            if(scanner.hasNextInt()){
                int test_val = scanner.nextInt();
                if (test_val > 0 && test_val <= 4) {
                    choice = test_val;
                    scanner.nextLine();
                }
                else{
                    System.out.println("Error : Enter a valid choice from the list!");
                }
            }
            else{
                System.out.println("Error : Invalid Data Type! (KINDLY DISPLAY SOME LEVEL OF INTELLIGENCE.)");
                scanner.nextLine();
            }

            
            switch (choice) {
                case 1:
                    break;

                case 2:

                    System.out.print("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n");
                    System.out.print("Enter Book Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter Book ID: ");
                    if(scanner.hasNextInt())
                    {
                        int bookID = scanner.nextInt();
                        System.out.print("Book successfully added");
                    }

                    else{
                        System.out.println("Error: Invalid Book ID!");
                        System.out.println("Error: Try again with an integer Book ID.");
                        scanner.nextLine();
                    }
                    
                    System.out.print("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n");
                    break;

                case 3:
                    System.out.print("\n<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");
                    System.out.print("Enter Book ID to remove: ");
                    if(scanner.hasNextInt()){
                        int removeID = scanner.nextInt();
                        System.out.print("Book removed successfully. \n");
                    }
                    else{
                        System.out.println("Error: Invalid value entered!");
                        System.out.println("Error: Try again using a valid Book ID");
                        scanner.nextLine();
                    }
                    
                    System.out.print("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");
                    break;
                    
                case 4:
                    System.out.println("Exiting... Goodbye!");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
            
    }
}