package Lesson_3.Task_1HW;


import java.io.*;

public class Program {
    public static void main(String[] args) {

        //Создание студента
        Student student = new Student("Василий", 18, 6.17);
        System.out.println(student.toString());

        //Сериализация студента
        try (FileOutputStream fileOut = new FileOutputStream("src/main/java/Lesson_3/Task_1HW/userdata.bin");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)){
            out.writeObject(student);
            System.out.println("Объект UserData сериализован.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Десериализация студента
        try (FileInputStream fileIn = new FileInputStream("src/main/java/Lesson_3/Task_1HW/userdata.bin");
             ObjectInputStream in = new ObjectInputStream(fileIn))
        {
            student = (Student) in.readObject();
            System.out.println("Объект UserData десериализован.");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        //Вывод десериализованного студента
        System.out.println(student.toString());

        //Причина:
        System.out.println("Причина, по которой мы не смогли восстановить GPA - transient " +
                "— это модификатор, указываемый перед полем класса " +
                "(подобно другим модификаторам, таким как public, final и т.д.)\n" +
                "для обозначения того, что данное поле не должно быть сериализовано. ");
    }
}
