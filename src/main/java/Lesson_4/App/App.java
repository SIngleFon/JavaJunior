package Lesson_4.App;

import Lesson_4.models.Courses;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class App {
    /**
     * Создайте базу данных (например, SchoolDB).
     * В этой базе данных создайте таблицу Courses с полями id (ключ), title, и duration.
     * Настройте Hibernate для работы с вашей базой данных.
     * Создайте Java-класс Course, соответствующий таблице Courses, с необходимыми аннотациями Hibernate.
     * Используя Hibernate, напишите код для вставки, чтения, обновления и удаления данных в таблице Courses.
     * Убедитесь, что каждая операция выполняется в отдельной транзакции.
     */
    static SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Courses.class)
            .buildSessionFactory();

    public static void main(String[] args) {
        //Create Course
        Courses courses = Courses.create();

        //Insert to DB
        insertDataWithHibernate(courses);

        //Read from DB with id
        System.out.println("Retrieved student object: " +readDataWithHibernate(45)+"\n");

        //Read all data from DB
//        List<Courses> list = loadAllData(Courses.class);
//        for (Courses lists : list) {
//            System.out.println(lists);
//        }

        //Update course DB with id
        updateDataWithHibernate(65);

        //Delete Courses from DB
        deleteDataWithHibernate(58);

    }


    public static void insertDataWithHibernate(Courses courses) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(courses);
            System.out.println("Object [" + courses+ "] save successfully\n");
            session.getTransaction().commit();
            session.close();
        }
    }

    private static <T> List<T> loadAllData(Class<T> type) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);
        List<T> data = session.createQuery(criteria).getResultList();
        session.close();
        return data;
    }

    public static Courses readDataWithHibernate(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Courses retrievedCourses = session.get(Courses.class, id);
            if (retrievedCourses == null){
                System.out.println("Not found Courses with this ID: " + id+"\n");
            }
            session.close();
            return retrievedCourses;
        }
    }

    public static void updateDataWithHibernate(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Courses courses = readDataWithHibernate(id);
            if (courses != null){
                Courses temp = new Courses(courses.getId(), courses.getTitle(), courses.getDuration());
                courses.updateName();
                courses.updateAge();
                System.out.println("Courses successfully updated. \n\t\tLast info: "+temp+"\n" +
                        "\t\tNew info: "+courses+"\n");
                session.update(courses);

            }
            session.getTransaction().commit();
            session.close();
        }
    }

    public static void deleteDataWithHibernate(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Courses courses = readDataWithHibernate(id);
            if (courses != null) {
                session.delete(courses);
                System.out.println("Object ["+courses+ "] student delete successfully\n");
            }
            session.getTransaction().commit();
            session.close();
        }
    }
}