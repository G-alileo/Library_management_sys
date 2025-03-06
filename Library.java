import java.io.*;
import java.util.ArrayList;

public class Library {
    private ArrayList<Book> books;
    private static final String BOOK_LIST = "books_data.txt";

    public Library() {
        this.books = loadBooks();
    }

    public void addBook(String title, String author, int bookID) {
        books.add(new Book(title, author, bookID));
        saveBooks();
        System.out.println("Book added successfully.");
    }

    public void removeBook(int bookID) {
        books.removeIf(book -> book.getBookId()== bookID);
        saveBooks();
        System.out.println("Book removed successfully.");
    }

    public void listBooks() {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    private void saveBooks() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BOOK_LIST))) {
            oos.writeObject(books);
        } catch (IOException e) {
            System.out.println("Error saving book data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private ArrayList<Book> loadBooks() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BOOK_LIST))) {
            return (ArrayList<Book>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}
