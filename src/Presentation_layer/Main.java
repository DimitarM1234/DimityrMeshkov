package Presentation_layer;

import Business_logic_layer.BookService;
import Business_logic_layer.LoanService;
import Data_access_layer.repo.BookRepository;
import Data_access_layer.repo.LoanRepository;
import Data_access_layer.repo.StudentRepository;

public class Main {
    public static void main(String[] args) {
        BookRepository bookRepository = new BookRepository();
        StudentRepository studentRepository = new StudentRepository();
        LoanRepository loanRepository = new LoanRepository();

        BookService bookService = new BookService(bookRepository);
        LoanService loanService = new LoanService(bookRepository, loanRepository, studentRepository);

        LibraryMenu menu = new LibraryMenu(bookService, loanService);
        menu.start();
    }
}
