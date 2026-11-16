package Data_access_layer.repo;

import Data_access_layer.models.Book;
import Data_access_layer.models.Student;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {
    private List<Student> students = new ArrayList<>();

    public void save(Student student) {

        students.add(student);
    }

    public List<Student> findAll() {

        return new ArrayList<>(students);
    }
}
