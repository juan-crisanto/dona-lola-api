package com.donalola.core.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Slf4j
public abstract class BaseDaoConfig {

    final static String HIBERNATE_DEFAULT_SCHEMA = "hibernate.default_schema";

    private final ConfigDaoProperties configDaoProperties;

    public BaseDaoConfig(ConfigDaoProperties configDaoProperties) {
        this.configDaoProperties = configDaoProperties;
    }

    public LocalContainerEntityManagerFactoryBean localEntityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource());
        localContainerEntityManagerFactoryBean.setPackagesToScan(configDaoProperties.getPackagesToScan());
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());

        Properties jpaProperties = new Properties();
        jpaProperties.setProperty(HIBERNATE_DEFAULT_SCHEMA, configDaoProperties.getDatabaseSchema());
        jpaProperties.setProperty("hibernate.temp.use_jdbc_metadata_defaults", "false");

        localContainerEntityManagerFactoryBean.setJpaProperties(jpaProperties);
        localContainerEntityManagerFactoryBean.setPersistenceUnitName(configDaoProperties.getPersistenceUnitName());

        return localContainerEntityManagerFactoryBean;
    }

    public JpaTransactionManager transactionManagerFactory() {
        EntityManagerFactory factory = localEntityManagerFactoryBean().getObject();
        return new JpaTransactionManager(factory);
    }

    public DataSource dataSource() {
        DataSource dataSource = null;
        if (configDaoProperties.useJNDI()) {
            try {
                InitialContext ctx = new InitialContext();
                dataSource = (DataSource) ctx.lookup(configDaoProperties.getDataBaseJNDI());
            } catch (NamingException e) {
                log.error("Error while looking up for datasource", e);
            }
        } else {
            dataSource = new DriverManagerDataSource();
            ((DriverManagerDataSource) dataSource).setDriverClassName(configDaoProperties.getDriverClassName());
            ((DriverManagerDataSource) dataSource).setUrl(configDaoProperties.getJdbcUrl());
            ((DriverManagerDataSource) dataSource).setUsername(configDaoProperties.getUsername());
            ((DriverManagerDataSource) dataSource).setPassword(configDaoProperties.getCredentials());
        }
        return dataSource;
    }

    public HibernateJpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setGenerateDdl(false);
        hibernateJpaVendorAdapter.setShowSql(configDaoProperties.showSql());
        hibernateJpaVendorAdapter.setDatabasePlatform(configDaoProperties.getDataBasePlatform());
        return hibernateJpaVendorAdapter;
    }
}
