package Data_access_layer.repo;

import Data_access_layer.models.Book;
import Data_access_layer.models.Loan;
import Data_access_layer.models.Student;
import java.util.ArrayList;
import java.util.List;

public class LoanRepository {
    private List<Loan> loans = new ArrayList<>();
    public void save(Loan loan) {
        loans.add(loan);
    }
    public List<Loan> findAll() {
        return new ArrayList<>(loans);
    }
    public int countActiveByStudent(Student student) {
        int count = 0;
        for (Loan loan : loans) {
            if (loan.getStudent().getEmail().equalsIgnoreCase(student.getEmail()) && loan.isReturned() == false) {
                count++;
            }
        }
        return count;
    }
    public Loan findActiveLoan(Student student, Book book) {
        for (Loan loan : loans) {
            if (loan.getStudent().getEmail().equalsIgnoreCase(student.getEmail()) && loan.getBook() == book && loan.isReturned() == false) {
                return loan;
            }
        }
        return null;
    }
}
