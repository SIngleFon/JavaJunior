package Lesson_4.models;

import javax.persistence.*;
import java.util.Random;
@Entity
@Table(name = "courses")
public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private int duration;
    private static final Random r = new Random();

    public Courses(int id, String title, int duration) {
        this.id = id;
        this.title = title;
        this.duration = duration;
    }
    public Courses(){

    }
    private static final String[] titles = new String[] {"Алгебра", "Русский язык", "Английский язык", "Геометрия", "Обществознание", "География"};
    public Courses(String title, int duration){
        this.title = title;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    public static Courses create(){
        return new Courses(titles[r.nextInt(titles.length)], r.nextInt(45, 91));
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Courses{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                '}';
    }

    public void updateAge(){
        duration = r.nextInt(45, 91);
    }

    public void updateName(){
        title = titles[r.nextInt(titles.length)];
    }
}
