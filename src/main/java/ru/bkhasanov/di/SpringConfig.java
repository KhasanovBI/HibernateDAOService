package ru.bkhasanov.di;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import ru.bkhasanov.settings.DBSettings;
import ru.bkhasanov.dao.CommentDAO;
import ru.bkhasanov.dao.PostDAO;
import ru.bkhasanov.service.CommentService;
import ru.bkhasanov.service.PostService;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by bulat on 07.03.17.
 */

@Configuration
public class SpringConfig {
    @Inject
    DataSource dataSource;

    @Bean
    JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    Properties hibernateProperties() {
        Properties hiberProps = new Properties();
        try (FileInputStream inputStream = new FileInputStream("src/main/resources/hibernate.properties")) {
            hiberProps.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hiberProps;
    }

    @Bean
    LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan("ru.bkhasanov.model");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    CommentDAO commentJdbcDAO() {
        return new CommentDAO(jdbcTemplate());
    }

    @Bean
    CommentService commentService() {
        return new CommentService();
    }

    @Bean
    PostDAO postHibernateDAO() {
        return new PostDAO();
    }

    @Bean
    PostService postService() {
        return new PostService();
    }


    @Bean
    DBSettings settings() {
        Properties props = new Properties();
        try(FileInputStream inputStream = new FileInputStream("src/main/resources/db.properties")) {
            props.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new DBSettings(props);
    }

    @Bean
    String dbUrl() {
        return settings().getUrl();
    }

    @Bean
    String dbUser() {
        return settings().getUser();
    }

    @Bean
    String dbPassword() {
        return settings().getPassword();
    }

    @Bean
    DataSource dataSource() throws SQLException {
        final PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        pgSimpleDataSource.setUrl(dbUrl());
        pgSimpleDataSource.setUser(dbUser());
        pgSimpleDataSource.setPassword(dbPassword());
        return pgSimpleDataSource;
    }
}
