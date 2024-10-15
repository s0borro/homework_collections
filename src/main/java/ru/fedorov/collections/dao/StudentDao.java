package ru.fedorov.collections.dao;

import ru.fedorov.collections.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentDao {

    private List<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
    }

    public Optional<Student> findStudent(String name) {
        return students.stream()
                .filter(it -> it.getName().equals(name))
                .findFirst();
    }

    public void removeStudent(String name) {
        students.remove(findStudent(name));
    }

    public List<Student> getAllStudents() {
        return students;
    }
}
