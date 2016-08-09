/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.auth.server;

import java.util.Properties;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @author nholvoet
 */
@Slf4j
@Configuration
@Profile("H2server")
@EnableJpaRepositories(JPARepository.LOCATION)
public class H2ServerDatabaseConfig extends JPAConfig {
    

    @Bean
    @Override
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:tcp://localhost/~/DB");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }


    @Override
    public Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("show_sql", "false");
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        return properties;
    }

}
