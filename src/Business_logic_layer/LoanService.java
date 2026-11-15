package Business_logic_layer;

import Data_access_layer.models.Book;
import Data_access_layer.models.Loan;
import Data_access_layer.models.Student;
import Data_access_layer.repo.BookRepository;
import Data_access_layer.repo.LoanRepository;
import Data_access_layer.repo.StudentRepository;

public class LoanService {
    private static final int MAX_LOANS = 3;

    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;
    private final StudentRepository studentRepository;

    public LoanService(BookRepository bookRepository, LoanRepository loanRepository, StudentRepository studentRepository) {
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
        this.studentRepository = studentRepository;
    }

    public Student getOrCreateStudent(String firstName, String lastName, String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Student email cannot be empty");
        }
        for (Student student : studentRepository.findAll()) {
            if (student.getEmail().equalsIgnoreCase(email)) {
                return student;
            }
        }
        if (firstName == null || firstName.isBlank() || lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Student name cannot be empty");
        }
        Student newStudent = new Student(firstName, lastName, email);
        studentRepository.save(newStudent);
        return newStudent;
    }

    public void borrowBook(Student student, String isbn) {
        Book book = bookRepository.findByIsbn(isbn);
        if (book == null) {
            throw new IllegalArgumentException("Book not found");
        }
        if (bookRepository.isAvailableByIsbn(isbn) == false) {
            throw new IllegalStateException("Book is already borrowed");
        }
        if (loanRepository.countActiveByStudent(student) >= MAX_LOANS) {
            throw new IllegalStateException("Student already has " + MAX_LOANS + " unreturned books");
        }
        loanRepository.save(new Loan(book, student));
        bookRepository.markBorrowedByIsbn(isbn);
    }

    public void returnBook(Student student, String isbn) {
        Book book = bookRepository.findByIsbn(isbn);
        if (book == null) {
            throw new IllegalArgumentException("Book not found");
        }
        Loan loan = loanRepository.findActiveLoan(student, book);
        if (loan == null) {
            throw new IllegalStateException("This student has not borrowed this book");
        }
        loan.markReturned();
        bookRepository.markReturnedByIsbn(isbn);
    }
}