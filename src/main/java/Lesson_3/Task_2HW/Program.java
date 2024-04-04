package Lesson_3.Task_2HW;

import java.io.File;

import static Lesson_3.Task_2HW.StudentApp.*;

public class Program {
    public static void main(String[] args) {

        //Инициализация студента
        Student student = new Student("Василич", 16, 3.5);
        System.out.println("Инициализация: " + student.toString());

        //Сериализация - Десериализация
        File f = new File(FILE_JSON);
        if (f.exists() && !f.isDirectory()){
            student = loadTasksFromFile(FILE_JSON);  //Десериализация
        }
        else{
            saveStudentToFile(FILE_JSON, student); //Сериализация
            saveStudentToFile(FILE_XML, student); //Сериализация
        }

        //Вывод после десериализации
        System.out.println("После десериализации: " + student.toString());
    }
}
