package ru.fedorov.collections.validation;

import java.util.List;

public class StudentValidation {

    public boolean isStudentNameValid(String name) {
        if (name == null || name.isEmpty()) {
            System.out.println("Имя не может быть пустым. Пожалуйста, введите корректное имя.");
            return false;
        }
        char[] array = name.toCharArray();
        for (char ch : array) {
            if (!Character.isLetter(ch)) {
                System.out.println("Имя должно содержать только буквы. Пожалуйста, введите имя снова.");
                return false;
            }
        }
        return true;
    }

    public boolean isStudentMarksValid(List<Integer> marks) {
        if (marks == null || marks.isEmpty()) {
            System.out.println("Оценки не могут быть пустыми. Пожалуйста, введите корректные оценки.");
            return false;
        }
        return true;
    }
}
