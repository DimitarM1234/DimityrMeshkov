package Business_logic_layer;

import Data_access_layer.models.Book;
import Data_access_layer.repo.BookRepository;
import java.util.ArrayList;
import java.util.List;

public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Rule: title cannot be empty, ISBN must be unique
    public void addBook(Book book) {
        if (book.getTitle() == null || book.getTitle().isBlank()) {
            throw new IllegalArgumentException("Book title cannot be empty");
        }
        if (book.getIsbn() == null || book.getIsbn().isBlank()) {
            throw new IllegalArgumentException("ISBN cannot be empty");
        }
        if (bookRepository.existsByIsbn(book.getIsbn()) == true) {
            throw new IllegalArgumentException("A book with this ISBN already exists");
        }
        bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book searchByTitle(String title) {
        Book book = bookRepository.findByTitle(title);
        if (book == null) {
            throw new IllegalArgumentException("Book not found");
        }
        return book;
    }

    // Edit book info (ISBN is used to find the book, so it stays the same)
    public void editBook(String isbn, String newTitle, String newAuthor, int newYear) {
        Book book = bookRepository.findByIsbn(isbn);
        if (book == null) {
            throw new IllegalArgumentException("Book not found");
        }
        if (newTitle == null || newTitle.isBlank()) {
            throw new IllegalArgumentException("Book title cannot be empty");
        }
        book.setTitle(newTitle);
        book.setAuthor(newAuthor);
        book.setYear(newYear);
    }

    // Rule: cannot delete a borrowed book
    public void deleteBook(String isbn) {
        if (bookRepository.existsByIsbn(isbn) == false) {
            throw new IllegalArgumentException("Book not found");
        }
        if (bookRepository.isAvailableByIsbn(isbn) == false) {
            throw new IllegalStateException("Cannot delete a borrowed book");
        }
        bookRepository.deleteByIsbn(isbn);
    }

    public boolean isAvailable(String isbn) {
        if (bookRepository.existsByIsbn(isbn) == false) {
            throw new IllegalArgumentException("Book not found");
        }
        return bookRepository.isAvailableByIsbn(isbn);
    }

    public List<Book> getAvailableBooks() {
        List<Book> available = new ArrayList<>();
        for (Book book : bookRepository.findAll()) {
            if (bookRepository.isAvailableByIsbn(book.getIsbn()) == true) {
                available.add(book);
            }
        }
        return available;
    }
}