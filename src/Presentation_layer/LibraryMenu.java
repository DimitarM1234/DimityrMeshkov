package Presentation_layer;

import Business_logic_layer.BookService;
import Business_logic_layer.LoanService;
import Data_access_layer.models.Book;
import Data_access_layer.models.Student;
import java.util.List;
import java.util.Scanner;

public class LibraryMenu {
    Scanner sc = new Scanner(System.in);
    private final BookService bookService;
    private final LoanService loanService;

    public LibraryMenu(BookService bookService, LoanService loanService) {
        this.bookService = bookService;
        this.loanService = loanService;
    }

    public void start() {
        int option = -1;
        while (option != 0) {
            System.out.println();
            System.out.println("-----Library Menu-----");
            System.out.println("1. Add Book");
            System.out.println("2. Show all books");
            System.out.println("3. Search book");
            System.out.println("4. Edit book");
            System.out.println("5. Delete book");
            System.out.println("6. Borrow book");
            System.out.println("7. Return book");
            System.out.println("8. Show available books");
            System.out.println("0. Exit");
            System.out.println("Choose an option");
            try {
                option = Integer.parseInt(sc.nextLine());
                switch (option) {
                    case 1:
                        addBook();
                        break;
                    case 2:
                        showAllBooks();
                        break;
                    case 3:
                        searchBook();
                        break;
                    case 4:
                        editBook();
                        break;
                    case 5:
                        deleteBook();
                        break;
                    case 6:
                        borrowBook();
                        break;
                    case 7:
                        returnBook();
                        break;
                    case 8:
                        showAvailableBooks();
                        break;
                    case 0:
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid input");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number");
                option = -1;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void addBook() {
        System.out.println("Title:");
        String title = sc.nextLine();
        System.out.println("Author:");
        String author = sc.nextLine();
        System.out.println("Year:");
        int year = Integer.parseInt(sc.nextLine());
        System.out.println("ISBN:");
        String isbn = sc.nextLine();
        bookService.addBook(new Book(title, author, year, isbn));
        System.out.println("Book added");
    }

    private void showAllBooks() {
        List<Book> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books yet");
        }
        for (Book book : books) {
            printBookWithStatus(book);
        }
    }

    private void searchBook() {
        System.out.println("Title:");
        String title = sc.nextLine();
        Book book = bookService.searchByTitle(title);
        printBookWithStatus(book);
    }

    private void editBook() {
        System.out.println("ISBN of the book to edit:");
        String isbn = sc.nextLine();
        System.out.println("New title:");
        String title = sc.nextLine();
        System.out.println("New author:");
        String author = sc.nextLine();
        System.out.println("New year:");
        int year = Integer.parseInt(sc.nextLine());
        bookService.editBook(isbn, title, author, year);
        System.out.println("Book edited");
    }

    private void deleteBook() {
        System.out.println("ISBN of the book to delete:");
        String isbn = sc.nextLine();
        bookService.deleteBook(isbn);
        System.out.println("Book deleted");
    }

    private void borrowBook() {
        Student student = readStudent();
        System.out.println("ISBN of the book:");
        String isbn = sc.nextLine();
        loanService.borrowBook(student, isbn);
        System.out.println("Book borrowed");
    }

    private void returnBook() {
        Student student = readStudent();
        System.out.println("ISBN of the book:");
        String isbn = sc.nextLine();
        loanService.returnBook(student, isbn);
        System.out.println("Book returned");
    }

    private void showAvailableBooks() {
        List<Book> books = bookService.getAvailableBooks();
        if (books.isEmpty()) {
            System.out.println("No available books");
        }
        for (Book book : books) {
            System.out.println(book);
        }
    }

    private Student readStudent() {
        System.out.println("Student email:");
        String email = sc.nextLine();
        System.out.println("Student first name (only needed for a new student):");
        String firstName = sc.nextLine();
        System.out.println("Student last name (only needed for a new student):");
        String lastName = sc.nextLine();
        return loanService.getOrCreateStudent(firstName, lastName, email);
    }

    private void printBookWithStatus(Book book) {
        if (bookService.isAvailable(book.getIsbn()) == true) {
            System.out.println(book + " - AVAILABLE");
        } else {
            System.out.println(book + " - BORROWED");
        }
    }
}