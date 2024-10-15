package ru.fedorov.collections;

import ru.fedorov.collections.model.Student;
import ru.fedorov.collections.service.StudentService;
import ru.fedorov.collections.validation.StudentValidation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class StudentApplication {

    private static final StudentService service = new StudentService();
    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentValidation validation = new StudentValidation();

    public static void main(String[] args) {
        while (true) {
            displayMenu();
            String choice = scanner.nextLine();
            handleMenuChoice(choice);
        }
    }

    private static void displayMenu() {
        System.out.println("Выберите необходимое действие:");
        System.out.println("a. Добавьте нового ученика;");
        System.out.println("b. Удалите ученика;");
        System.out.println("c. Обновите оценку ученика;");
        System.out.println("d. Просмотр оценок всех учащихся;");
        System.out.println("e. Просмотр оценок конкретного учащегося.");
        System.out.printf("Ваш выбор: ");
    }

    private static void handleMenuChoice(String choice) {
        switch (choice) {
            case "a":
                addStudent();
                break;
            case "b":
                removeStudent();
                break;
            case "c":
                updateStudentMark();
                break;
            case "d":
                displayAllStudents();
                break;
            case "e":
                displaySpecificStudentMarks();
                break;
            default:
                System.out.printf("Некорректный выбор действия.");
        }
        System.out.println("---------------");
    }

    private static void addStudent() {
        boolean isNameValid;
        String nameToAdd;
        do {
            System.out.printf("Введите имя (только буквы): ");
            nameToAdd = scanner.nextLine();
            isNameValid = validation.isStudentNameValid(nameToAdd);
        } while (!isNameValid);

        boolean isMarksValid;
        List<Integer> marksToAdd;
        do {
            System.out.printf("Введите оценки через пробел: ");
            marksToAdd = parseMarks(scanner.nextLine());
            isMarksValid = validation.isStudentMarksValid(marksToAdd);
        } while (!isMarksValid);

        service.addStudentByName(nameToAdd, marksToAdd);
    }

    private static void removeStudent() {
        System.out.printf("Введите имя ученика, данные которого необходимо удалить (только буквы): ");
        String nameToRemove = scanner.nextLine();

        if (service.findStudentByName(nameToRemove).isPresent()) {
            service.removeStudentByName(nameToRemove);
            System.out.println("Ученик удален.");
        } else {
            System.out.println("Ученика с введенным именем не существует.");
        }
    }

    private static void updateStudentMark() {
        System.out.printf("Введите имя ученика, оценку которого необходимо изменить (только буквы): ");
        String nameToUpdate = scanner.nextLine();
        Optional<Student> studentToUpdate = service.findStudentByName(nameToUpdate);

        if (studentToUpdate.isPresent()) {
            displayMarks(studentToUpdate.get());

            int numberOfMark = getNumberOfMark(studentToUpdate.get());
            System.out.printf("Введите новое значение: ");
            int newValue = scanner.nextInt();

            service.updateMarkByNameAndNumberOfMark(nameToUpdate, numberOfMark - 1, newValue);
        } else {
            System.out.println("Ученика с введенным именем не существует.");
        }
    }

    private static void displayAllStudents() {
        System.out.println("");
        System.out.println("Список всех учеников и их оценок:");
        for (var student : service.getAllStudents()) {
            System.out.println("Ученик: " + student.getName());
            System.out.println("Оценки: " + student.getMarks());
            System.out.println("");
        }
        System.out.println("");
    }

    private static void displaySpecificStudentMarks() {
        System.out.printf("Введите имя ученика, оценки которого вы хотите увидеть (только буквы): ");
        Optional<Student> studentToShow = service.findStudentByName(scanner.nextLine());

        if (studentToShow.isPresent()) {
            System.out.println("Ученик: " + studentToShow.get().getName());
            System.out.println("Оценки: " + studentToShow.get().getMarks());
        } else {
            System.out.println("Ученика с введенным именем не существует.");
        }
    }

    private static List<Integer> parseMarks(String input) {
        List<Integer> marks = new ArrayList<>();
        for (String mark : input.split(" ")) {
            marks.add(Integer.parseInt(mark));
        }
        return marks;
    }

    private static void displayMarks(Student student) {
        System.out.println("Ниже выведены оценки ученика с нумерацией:");
        for (int i = 0; i < student.getMarks().size(); i++) {
            System.out.println((i + 1) + ". " + student.getMarks().get(i));
        }
    }

    private static int getNumberOfMark(Student student) {
        int numberOfMark;
        while (true) {
            System.out.printf("Введите номер оценки, которую необходимо изменить: ");
            numberOfMark = scanner.nextInt();

            if (numberOfMark >= 1 && numberOfMark <= student.getMarks().size()) {
                return numberOfMark;
            } else {
                System.out.println("Ошибка: введите номер оценки в пределах от 1 до " + student.getMarks().size());
            }
        }
    }
}
