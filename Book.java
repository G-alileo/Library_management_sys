public class Book {
    private String title;
    private String author;
    private int bookID;
    private boolean isavailable;

    public Book(String title, String author, int bookID)
    {
        this.title  = title;
        this.author = author;
        this.bookID = bookID;
        this.isavailable = true;
    }

    public String getTitle(){
        return title;
    }

    public String getAuthor(){
        return author;
    }

    public int getBookId(){
        return bookID;
    }

    public boolean isAvailable(){
        return isavailable;
    }

    public void setAvailable(boolean availabe){
        isavailable = availabe;
    }

    @Override
    public String toString() {
        return "Book ID: " + bookID + ", Title: " + title + ", Author: " + author + ", Available: " + isavailable;
    }   
}


