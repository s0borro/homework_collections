package ru.fedorov.collections.service;

import ru.fedorov.collections.dao.StudentDao;
import ru.fedorov.collections.model.Student;

import java.util.List;
import java.util.Optional;

public class StudentService {

    private final StudentDao studentDao = new StudentDao();

    public void addStudentByName(String name, List<Integer> marks) {
        studentDao.addStudent(new Student(name, marks));
    }

    public void removeStudentByName(String name) {
        studentDao.removeStudent(name);
    }

    public Optional<Student> findStudentByName(String name) {
        return studentDao.findStudent(name);
    }

    public void updateMarkByNameAndNumberOfMark(String name, Integer numberOfMark, Integer newValue) {
        studentDao.findStudent(name)
                .ifPresent(it -> it.getMarks()
                        .set(numberOfMark, newValue));
    }

    public List<Student> getAllStudents() {
        return studentDao.getAllStudents();
    }
}
