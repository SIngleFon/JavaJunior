package Lesson_3.Task_2HW;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentApp {
    public static final String FILE_JSON = "src/main/java/Lesson_3/Task_2HW/student.json";
    public static final String FILE_XML = "src/main/java/Lesson_3/Task_2HW/student.xml";

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final XmlMapper xmlMapper = new XmlMapper();

    public static void saveStudentToFile(String fileName, Student student) {
        try {
            if (fileName.endsWith(".json")) {
                objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                objectMapper.writeValue(new File(fileName), student);
            }
            else if (fileName.endsWith(".xml")) {
                xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                xmlMapper.writeValue(new File(fileName), student);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static Student loadTasksFromFile(String fileName){
        Student student = new Student();
        File file = new File(fileName);
        try {
            if (file.exists()) {
                if (fileName.endsWith(".json")) {
                    student = objectMapper.readValue(file, objectMapper.getTypeFactory().constructType(Student.class));
                }
                else if (fileName.endsWith(".xml")) {
                    student = xmlMapper.readValue(file,
                            xmlMapper.getTypeFactory().constructCollectionType(List.class, Student.class));
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return student;
    }
}
