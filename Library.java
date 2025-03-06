import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books;
    private String filePath;
    private int nextBookId;

    public Library(String filePath) {
        this.filePath = filePath;
        this.books = new ArrayList<>();
        this.nextBookId = 1;
        initializeLibrary();
    }

    private void initializeLibrary() {
        File file = new File(filePath);
        
        // Create the file if it doesn't exist
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists. Loading books...");
                loadBooksFromFile();
            }
        } catch (IOException e) {
            System.out.println("Error initializing library: " + e.getMessage());
        }
    }

    private void loadBooksFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                // Parse book data from line
                try {
                    String[] parts = line.split("\"\\s+\"");
                    if (parts.length >= 3) {
                        String title = parts[0].replace("\"Title\" : ", "").trim();
                        String author = parts[1].replace("Author\" : ", "").trim();
                        String idPart = parts[2].replace("Book ID\" : ", "").trim();
                        String availabilityPart = "true";
                        
                        if (parts.length > 3) {
                            availabilityPart = parts[3].replace("Available\" : ", "").trim();
                        }
                        
                        int id = Integer.parseInt(idPart);
                        boolean isAvailable = Boolean.parseBoolean(availabilityPart);
                        
                        Book book = new Book(title, author, id);
                        book.setAvailable(isAvailable);
                        books.add(book);
                        
                        // Update nextBookId to be greater than the highest ID found
                        if (id >= nextBookId) {
                            nextBookId = id + 1;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error parsing book: " + line + " - " + e.getMessage());
                }
            }
            System.out.println("Successfully loaded " + books.size() + " books.");
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }

    public void listBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
            return;
        }
        
        System.out.println("\nLibrary Books:");
        System.out.println("-------------------------------------------------------------");
        for (Book book : books) {
            System.out.println(book);
        }
        System.out.println("-------------------------------------------------------------");
    }

    public void listAvailableBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
            return;
        }
        
        System.out.println("\nAvailable Books:");
        System.out.println("-------------------------------------------------------------");
        boolean foundAvailable = false;
        for (Book book : books) {
            if (book.isAvailable()) {
                System.out.println(book);
                foundAvailable = true;
            }
        }
        
        if (!foundAvailable) {
            System.out.println("No books are currently available for borrowing.");
        }
        System.out.println("-------------------------------------------------------------");
    }

    public void addBook(String title, String author) {
        try {
            // Create book with auto-generated ID
            Book newBook = new Book(title, author, nextBookId++);
            books.add(newBook);
            
            // Save to file
            saveToFile();
            System.out.println("Book successfully added with ID: " + newBook.getBookId());
        } catch (Exception e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    public boolean removeBook(int bookId) {
        Book bookToRemove = null;
        
        for (Book book : books) {
            if (book.getBookId() == bookId) {
                bookToRemove = book;
                break;
            }
        }
        
        if (bookToRemove != null) {
            books.remove(bookToRemove);
            saveToFile();
            System.out.println("Book with ID " + bookId + " successfully removed.");
            return true;
        } else {
            System.out.println("Book with ID " + bookId + " not found!");
            return false;
        }
    }

    public boolean borrowBook(int bookId) {
        for (Book book : books) {
            if (book.getBookId() == bookId) {
                if (book.isAvailable()) {
                    book.setAvailable(false);
                    saveToFile();
                    System.out.println("Book '" + book.getTitle() + "' successfully borrowed.");
                    return true;
                } else {
                    System.out.println("Sorry, this book is not available for borrowing.");
                    return false;
                }
            }
        }
        
        System.out.println("Book with ID " + bookId + " not found!");
        return false;
    }

    public boolean returnBook(int bookId) {
        for (Book book : books) {
            if (book.getBookId() == bookId) {
                if (!book.isAvailable()) {
                    book.setAvailable(true);
                    saveToFile();
                    System.out.println("Book '" + book.getTitle() + "' successfully returned.");
                    return true;
                } else {
                    System.out.println("This book was not borrowed.");
                    return false;
                }
            }
        }
        
        System.out.println("Book with ID " + bookId + " not found!");
        return false;
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Book book : books) {
                writer.write("\"Title\" : " + book.getTitle() + 
                           "  \"Author\" : " + book.getAuthor() + 
                           "  \"Book ID\" : " + book.getBookId() + 
                           "  \"Available\" : " + book.isAvailable());
                writer.newLine();
            }
            System.out.println("Successfully saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }
}