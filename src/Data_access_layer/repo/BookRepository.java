package Data_access_layer.repo;

import Data_access_layer.models.Book;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookRepository {
    private final List<Book> books = new ArrayList<>();
    private final Set<Book> borrowedBooks = new HashSet<>();
    public void save(Book book) {
        books.add(book);
    }
    public List<Book> findAll() {
        return new ArrayList<>(books);
    }
    public Book findByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }
    public Book findByIsbn(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equalsIgnoreCase(isbn)) {
                return book;
            }
        }
        return null;
    }

    public boolean existsByIsbn(String isbn) {
        return findByIsbn(isbn) != null;
    }

    public boolean deleteByTitle(String title) {
        Book book = findByTitle(title);
        if (book != null) {
            books.remove(book);
            borrowedBooks.remove(book);
            return true;
        }
        return false;
    }

    public boolean deleteByIsbn(String isbn) {
        Book book = findByIsbn(isbn);
        if (book != null) {
            books.remove(book);
            borrowedBooks.remove(book);
            return true;
        }
        return false;
    }

    public boolean isAvailable(String title) {
        Book book = findByTitle(title);
        return book != null && !borrowedBooks.contains(book);
    }

    public boolean isAvailableByIsbn(String isbn) {
        Book book = findByIsbn(isbn);
        return book != null && !borrowedBooks.contains(book);
    }

    public void markBorrowed(String title) {
        Book book = findByTitle(title);
        if (book != null) {
            borrowedBooks.add(book);
        }
    }

    public void markBorrowedByIsbn(String isbn) {
        Book book = findByIsbn(isbn);
        if (book != null) {
            borrowedBooks.add(book);
        }
    }

    public void markReturned(String title) {
        Book book = findByTitle(title);
        if (book != null) {
            borrowedBooks.remove(book);
        }
    }

    public void markReturnedByIsbn(String isbn) {
        Book book = findByIsbn(isbn);
        if (book != null) {
            borrowedBooks.remove(book);
        }
    }
}