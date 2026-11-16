package Data_access_layer.models;

import java.time.LocalDate;

public class Loan {
    private Book book;
    private Student student;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    public Loan(Book book, Student student) {
        this.book = book;
        this.student = student;
        this.borrowDate = LocalDate.now();
    }
    public Book getBook() {
        return book;
    }

    public Student getStudent() {
        return student;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public boolean isReturned() {
        return returnDate != null;
    }

    public void markReturned() {
        this.returnDate = LocalDate.now();
    }

    @Override
    public String toString() {
        return "Loan{book='" + book.getTitle() + "', student='" + student.getFirstName() + " " + student.getLastName() + "', borrowed=" + borrowDate + ", returned=" + (returnDate == null ? "no" : returnDate) + "}";
    }
}
