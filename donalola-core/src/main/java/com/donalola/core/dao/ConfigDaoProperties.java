package com.donalola.core.dao;

public interface ConfigDaoProperties {

    boolean showSql();

    String getDataBasePlatform();

    String getPackagesToScan();

    /**
     * If returns true, the configurator try to get the datasource from application server using JNDI,
     * so the DataBaseJDNI() method must be implemented.
     * If returns false, the configurator will create the datasource using an external driver,
     * the JdbcUrl, username and password, so the methods getDriverClassName(), getDriverClassName(),
     * getJdbcUrl(), getUsername(), getCredentials() methods must be implemented.
     *
     * @return boolean
     */
    boolean useJNDI();

    String getDatabaseSchema();

    String getPersistenceUnitName();

    default String getDataBaseJNDI() {
        throw new IllegalArgumentException("JNDI hasn't been specified");
    }

    default String getDriverClassName() {
        throw new IllegalArgumentException("DriverClassName hasn't been specified");
    }

    default String getJdbcUrl() {
        throw new IllegalArgumentException("JdbcUrl hasn't been specified");
    }

    default String getUsername() {
        throw new IllegalArgumentException("Database username hasn't been specified");
    }


    default String getCredentials() {
        throw new IllegalArgumentException("Database credentials hasn't been specified");
    }
}
