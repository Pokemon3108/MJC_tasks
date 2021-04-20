package com.epam.esm.config;

import java.util.ResourceBundle;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DataSourceConfiguration {

    @Bean
    @Profile("dev")
    public DataSource embeddedDataSource() {

        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:sql/tables-dev.sql")
                .addScript("classpath:sql/fill-tables-h2.sql")
                .build();
    }

    @Bean
    @Profile("prod")
    public DataSource dataSource() {

        BasicDataSource dataSource = new BasicDataSource();
        ResourceBundle resource = ResourceBundle.getBundle("database");

        String url = resource.getString("db.url");
        dataSource.setUrl(url);

        String user = resource.getString("db.user");
        dataSource.setUsername(user);

        String pass = resource.getString("db.password");
        dataSource.setPassword(pass);

        String driver = resource.getString("db.driver");
        dataSource.setDriverClassName(driver);

        int poolMaxSize = Integer.parseInt(resource.getString("db.poolMaxSize"));
        dataSource.setMaxTotal(poolMaxSize);

        int poolInitSize = Integer.parseInt(resource.getString("db.initSize"));
        dataSource.setInitialSize(poolInitSize);

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {

        return new JdbcTemplate(dataSource);
    }

    @Bean
    public TransactionManager transactionManager(DataSource dataSource) {

        return new DataSourceTransactionManager(dataSource);
    }
}
