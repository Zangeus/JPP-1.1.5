package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;


import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {
    public static final String DBNAME = "hibernate_master";
    public static final String URL = "jdbc:mysql://localhost:3306/" + DBNAME;
    public static final String USERNAME = "root";
    public static final String PASSWORD = "12345678q";

    public static Logger LOGGER = Logger.getLogger(Util.class.getName());

    //JDBC
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            LOGGER.log(Level.INFO, "Соединение не было установлено");
            e.printStackTrace();
        }
        return connection;
    }

    //HIBERNATE
    private static SessionFactory sessionFactory;
    private static final String DIALECT = "org.hibernate.dialect.MySQLDialect";

    private static SessionFactory buildMySessionFactory() {
        if (sessionFactory != null) return sessionFactory;
        else try {
            System.out.println("--------------------------------------");
            System.out.println("Началось создание фабрики");
            System.out.println("Она имеет следующие параметры");
            System.out.println("URL: " + URL);
            System.out.println("USERNAME: " + USERNAME);
            System.out.println("PASSWORD: " + PASSWORD);
            System.out.println("DIALECT: " + DIALECT);
            System.out.println("--------------------------------------");
            Properties settings = new Properties();
            settings.put(Environment.URL, URL);
            settings.put(Environment.USER, USERNAME);
            settings.put(Environment.PASS, PASSWORD);
            settings.put(Environment.DIALECT, DIALECT);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(settings).build();

            MetadataSources metadataSources = new MetadataSources(serviceRegistry);
            metadataSources.addAnnotatedClass(User.class);
            Metadata metadata = metadataSources.buildMetadata();

            return metadata.getSessionFactoryBuilder().build();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING,
                    "При инициализации SessionFactory произошла ошибка");
            throw e;
        }
    }

    public static SessionFactory getSessionFactory() {
        return buildMySessionFactory();
    }

    //OTHER
    public static Logger getLOGGER() {
        return LOGGER;
    }
}
