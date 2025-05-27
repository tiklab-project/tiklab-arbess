package io.tiklab.arbess.support.util.configure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class DatabaseVersionConfigure {

    @Value("${jdbc.driverClassName}")
    private String sqlVersion;

    private final Logger logger = LoggerFactory.getLogger(DatabaseVersionConfigure.class);

    @PostConstruct
    public void init() {
        boolean isPgsql = !"com.mysql.cj.jdbc.Driver".equals(sqlVersion);
        System.setProperty("ARBESS_IS_PGSQL", String.valueOf(isPgsql));
        logger.info("Current database version {}", isPgsql ? "POSTGRESQL" : "MYSQL");
    }
}
