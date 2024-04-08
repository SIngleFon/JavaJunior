package Lesson_4.Session;

import Lesson_4.models.Courses;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class Program {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/";
        String user = "root";
        String password = "password";

        try(Connection connection = DriverManager.getConnection(url, user, password)){

            createDataBase(connection);
            System.out.println("База данных создана успешно!");

            useDataBase(connection);
            System.out.println("Используем базу данных");

            createTable(connection);
            System.out.println("Создаем таблицу в базе данных");

            for (int i = 0; i < 5; i++) {
                insertData(connection, Courses.create());
            }
            System.out.println("Вставляем данные в таблицу");

            Collection<Courses> courses = readData(connection);
            for (var course : courses){
                System.out.println(course);
            }
            System.out.println("Чтение базы данных прошло успешно!");

            for (var student: courses) {
                student.updateName();
                student.updateAge();
                updateData(connection, student);
            }
            System.out.println("Update data successfully");

//            for (var course: courses)
//                deleteData(connection, course.getId());
//            System.out.println("Delete data successfully");

            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private static Collection<Courses> readData(Connection connection) throws SQLException {
        ArrayList<Courses> studentsList = new ArrayList<>();
        String readDataSQL = "SELECT * FROM courses;";
        try (PreparedStatement statement = connection.prepareStatement(readDataSQL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                int duration = resultSet.getInt("duration");
                studentsList.add(new Courses(id, title, duration));
            }
            return studentsList;
        }
    }
    private static void updateData(Connection connection, Courses courses) throws SQLException {
        String updateDataSQL = "UPDATE courses SET title=?, duration=? WHERE id=?;";
        try (PreparedStatement statement = connection.prepareStatement(updateDataSQL)) {
            statement.setString(1, courses.getTitle());
            statement.setInt(2, courses.getDuration());
            statement.setInt(3, courses.getId());
            statement.executeUpdate();
        }
    }
    public static void createDataBase(Connection connection) throws SQLException {
        String createDataBaseMySQL = "CREATE DATABASE IF NOT EXISTS SchoolDB;";
        PreparedStatement statement = connection.prepareStatement(createDataBaseMySQL);
        statement.execute();
    }

    public static void useDataBase(Connection connection) throws SQLException{
        String useDataBaseSQL = "USE SchoolDB;";
        try (PreparedStatement statement = connection.prepareStatement(useDataBaseSQL)){
            statement.execute();
        }
    }

    public static void createTable(Connection connection) throws SQLException{
        String createTableSQL = "CREATE TABLE IF NOT EXISTS courses (id INT AUTO_INCREMENT PRIMARY KEY, title VARCHAR(255), duration INT);";
        try (PreparedStatement statement = connection.prepareStatement(createTableSQL)){
            statement.execute();
        }
    }

    private static void insertData(Connection connection, Courses courses) throws SQLException{
        String insertData = "INSERT INTO courses (title, duration) VALUES (?, ?);";
        try(PreparedStatement preparedStatement = connection.prepareStatement(insertData)){
            preparedStatement.setString(1, courses.getTitle());
            preparedStatement.setInt(2, courses.getDuration());
            preparedStatement.executeUpdate();
        }
    }
    private static void deleteData(Connection connection, int id) throws SQLException {
        String deleteDataSQL = "DELETE FROM courses WHERE id=?;";
        try (PreparedStatement statement = connection.prepareStatement(deleteDataSQL)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }
}
