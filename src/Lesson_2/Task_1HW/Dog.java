package Lesson_2.Task_1HW;

public class Dog extends Animal{
    private String food;

    public Dog(String name, int age, String food) {
        super(name, age);
        this.food = food;
    }

    public void makeSound(){
        System.out.println("Гав");
    }
}
