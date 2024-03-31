package Lesson_2.Task_1HW;

public class Cat extends Animal{
    private String food;

    public Cat(String name, int age, String food) {
        super(name, age);
        this.food = food;
    }

    public void makeSound(){
        System.out.println("Гав");
    }

}
