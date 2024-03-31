package Lesson_2.Task_1HW;


import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Program {
    /**
     * Создайте абстрактный класс "Animal" с полями "name" и "age".
     * Реализуйте два класса-наследника от "Animal" (например, "Dog" и "Cat") с уникальными полями и методами.
     * Создайте массив объектов типа "Animal" и с использованием Reflection API выполните следующие действия:
     * Выведите на экран информацию о каждом объекте.
     * Вызовите метод "makeSound()" у каждого объекта, если такой метод присутствует.
     */
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Animal[] animals = new Animal[]{
                new Dog("Барс", 2, "Пурина"),
                new Cat("Мур", 4, "Вискас"),
                new Cat("Василич", 10, "Флорида для котов"),
        };

        for (Animal animal : animals) {
            Class<? extends Animal> animalClass = animal.getClass();
            StringBuilder sb = new StringBuilder();
            // Проверяем, что класс является наследником Dog или Cat
            if (animalClass == Dog.class || animalClass == Cat.class) {
                sb.append(appenderSB(animal));
            }
            System.out.println(sb);
            Method[] methods = animalClass.getDeclaredMethods();
            for (Method method : methods){
                method.setAccessible(true);
                System.out.print(method.getName()+": ");
                method.invoke(animal);
            }
            System.out.println("-------------------------");
        }
    }
    public static StringBuilder appenderSB(Object obj) throws IllegalAccessException {
        Field[] fieldsExtender = obj.getClass().getDeclaredFields();
        Field[] fieldsParent = Animal.class.getDeclaredFields();
        StringBuilder stringBuilder = new StringBuilder(obj.getClass().getSimpleName() + ": ");
        //Поля родителя
        for (Field field : fieldsParent){
            field.setAccessible(true); // Разрешаем доступ к приватным полям
            stringBuilder.append(field.getName()).append(" = ").append(field.get(obj)).append(", ");
        }
        //Поля наследника
        for (Field field : fieldsExtender) {
            field.setAccessible(true); // Разрешаем доступ к приватным полям
            stringBuilder.append(field.getName()).append(" = ").append(field.get(obj));
        }
        return stringBuilder;
    };


}
