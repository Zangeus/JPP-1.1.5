package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();

            session.createNativeQuery("""
                    CREATE TABLE IF NOT EXISTS users (
                      `id` INT NOT NULL AUTO_INCREMENT,
                      `name` VARCHAR(45) NOT NULL,
                      `lastname` VARCHAR(45) NOT NULL,
                      `age` INT NULL,
                      PRIMARY KEY (`id`));""", User.class).executeUpdate();

            session.getTransaction().commit();
        } catch (Exception e) {
            Util.getLOGGER().log(Level.INFO, "Произошла ошибка создания таблицы...");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();

            session.createNativeQuery(
                    "DROP TABLE IF EXISTS users", User.class).executeUpdate();

            session.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("---------------------------------------------------");
            Util.getLOGGER().log(Level.INFO, "Не удалось удалить таблицу user");
            System.out.println("---------------------------------------------------");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {

            session.beginTransaction();
            session.persist(new User(name, lastName, age));
            session.getTransaction().commit();

            Util.getLOGGER().log(Level.INFO, name + " был добавлен в базу данных");
        } catch (Exception e) {
            Util.getLOGGER().log(Level.INFO, "При сохранении пользователя произошла ошибка");
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();

            User user = session.get(User.class, id);
            session.remove(user);
            Util.getLOGGER().log(Level.INFO, user + " был удален из базы данных");
            session.getTransaction().commit();

        } catch (Exception e) {
            Util.getLOGGER().log(Level.INFO, "При удалении пользователя произошла ошибка");
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {

            session.beginTransaction();
            users = session.createNativeQuery(
                    "SELECT * FROM users", User.class).getResultList();
            session.getTransaction().commit();

        } catch (Exception e) {
            Util.getLOGGER().log(Level.INFO, "При получении списка пользователей произошла ошибка");
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();

            session.createNativeQuery(
                    "DELETE FROM users", User.class).executeUpdate();

            session.getTransaction().commit();

        } catch (Exception e) {
            Util.getLOGGER().log(Level.INFO, "При очистке произошла ошибка");
            e.printStackTrace();
        }
    }
}

