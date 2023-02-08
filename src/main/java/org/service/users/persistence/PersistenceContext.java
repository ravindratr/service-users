package org.service.users.persistence;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
@EnableJpaRepositories(basePackages = "org.service.users.repositories")
public class PersistenceContext {
    private static final String DB_USERNAME="spring.datasource.username";
    private static final String DB_PASSWORD="spring.datasource.password";
    private static final String DB_URL ="spring.datasource.url";
    private static final String DB_DRIVER_CLASS="javax.persistence.jdbc.driver";


    @Autowired
    Environment env;
    @Bean
    public DataSource getDataSource(){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(env.getProperty(DB_DRIVER_CLASS));
        dataSource.setJdbcUrl(env.getProperty(DB_URL));
        dataSource.setUsername(env.getProperty(DB_USERNAME));
        dataSource.setPassword(env.getProperty(DB_PASSWORD));
        return dataSource;
    }

    public Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }
}
