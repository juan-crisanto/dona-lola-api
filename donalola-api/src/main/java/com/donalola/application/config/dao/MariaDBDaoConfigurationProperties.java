package com.donalola.application.config.dao;

import com.donalola.core.dao.ConfigDaoProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = MariaDBDaoConfigurationProperties.INSTANCE_QUALIFIER)
public class MariaDBDaoConfigurationProperties implements ConfigDaoProperties {

    public static final String INSTANCE_QUALIFIER = "foodPlaceDaoPropertiesConfig";

    @Value("${mariadb.showSql:false}")
    private boolean showSql;
    @Value("${mariadb.dataBasePlatform}")
    private String databasePlatform;
    @Value("${mariadb.packagesToScan}")
    private String packagesToScan;
    @Value("${mariadb.useJNDI:false}")
    private boolean useJNDI;
    @Value("${mariadb.driverClassName}")
    private String driverClassName;
    @Value("${mariadb.jdbcUrl}")
    private String jdbcUrl;
    @Value("${mariadb.dataBaseSchema}")
    private String dataBaseSchema;
    @Value("${mariadb.username}")
    private String username;
    @Value("${mariadb.credentials}")
    private String credentials;


    @Override
    public boolean showSql() {
        return this.showSql;
    }

    @Override
    public String getDataBasePlatform() {
        return this.databasePlatform;
    }

    @Override
    public String getPackagesToScan() {
        return this.packagesToScan;
    }

    @Override
    public boolean useJNDI() {
        return this.useJNDI;
    }

    @Override
    public String getDatabaseSchema() {
        return this.dataBaseSchema;
    }

    @Override
    public String getPersistenceUnitName() {
        return "mariaDBSqlDao";
    }

    @Override
    public String getDriverClassName() {
        return this.driverClassName;
    }

    @Override
    public String getJdbcUrl() {
        return this.jdbcUrl;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getCredentials() {
        return this.credentials;
    }
}
