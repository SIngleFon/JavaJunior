package Lesson_3.Task_2HW;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Student implements Externalizable {

    //region Поля
    private String name;
    private int age;
    private  double gpa;
    //endregion

    //region Конструкторы
    public Student(){

    }
    public Student(String name, int age, double GPA){
        this.name = name;
        this.age = age;
        this.gpa = GPA;
    }
    //endregion

    //region Методы
    public String getName() {
        return name;
    }

    public double getGpa() {
        return gpa;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Студент: " +
                "name='" + name + '\'' +
                ", age=" + age +
                ", GPA=" + gpa;
    }
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {

    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

    }
    //endregion
}
