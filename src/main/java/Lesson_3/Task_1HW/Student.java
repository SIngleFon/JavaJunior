package Lesson_3.Task_1HW;

import java.io.Serializable;

public class Student implements Serializable {

    //region Поля
    private String name;
    private int age;
    private transient double GPA;
    //endregion

    //region Конструкторы
    public Student(String name, int age, double GPA){
        this.name = name;
        this.age = age;
        this.GPA = GPA;
    }
    //endregion

    //region Методы
    public String getName() {
        return name;
    }

    public double getGPA() {
        return GPA;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", GPA=" + GPA +
                '}';
    }
    //endregion
}
