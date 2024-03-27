package Lesson_1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {
    public static void main(String[] args) {

        List<Integer> arr = Arrays.asList(2, 1, 1, 1, 0, 1, 5, 1, 7);

        System.out.printf("Среднее: %s%n", arr.stream()
                .filter(ar -> ar % 2 == 0)
                .anyMatch(ar -> ar >= 0)  // 0 - является четным числом(если так не считаете, поменять знак >= на >
                 ? arr.stream().filter(ar -> ar % 2 == 0)
                    .mapToInt(Integer::intValue)
                    .average().getAsDouble() : "Нет четных чисел");
    }
}
