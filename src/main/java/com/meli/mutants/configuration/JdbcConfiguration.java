package com.meli.mutants.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.postgresql.Driver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JdbcConfiguration {

    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    @Value("${jdbc.max-pool-size}")
    private Integer maximumPoolSize;

    @Value("${jdbc.leak-detection-threshold}")
    private Long leakDetectionThreshold;

    @Value("${jdbc.init-query}")
    private String initQuery;

    @Bean
    public DataSource dataSource() {

        System.out.println(jdbcUrl + " " + username + " " + password + " " + maximumPoolSize + " " + leakDetectionThreshold + " " + initQuery + " ");

        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setDriverClassName(Driver.class.getName());
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaximumPoolSize(maximumPoolSize);
        dataSource.setLeakDetectionThreshold(leakDetectionThreshold);
        dataSource.setConnectionInitSql(initQuery);

        return dataSource;
    }

}
