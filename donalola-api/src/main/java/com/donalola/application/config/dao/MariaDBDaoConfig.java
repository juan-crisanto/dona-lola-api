package com.donalola.application.config.dao;

import com.donalola.core.dao.BaseDaoConfig;
import com.donalola.core.dao.ConfigDaoProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"com.donalola.application.config.dao", "com.donalola.foodplaces.dao"},
        entityManagerFactoryRef = "mariaDbEntityManager", transactionManagerRef = "mariaDbTransactionManager")
public class MariaDBDaoConfig extends BaseDaoConfig {

    public MariaDBDaoConfig(@Qualifier(value = MariaDBDaoConfigurationProperties.INSTANCE_QUALIFIER) ConfigDaoProperties configDaoProperties) {
        super(configDaoProperties);
    }

    @Bean(name = "mariaDbEntityManager")
    @Primary
    @Override
    public LocalContainerEntityManagerFactoryBean localEntityManagerFactoryBean() {
        return super.localEntityManagerFactoryBean();
    }

    @Bean(name = "mariaDbTransactionManager")
    @Override
    public JpaTransactionManager transactionManagerFactory() {
        return super.transactionManagerFactory();
    }

    @Bean(name = "mariaDbDataSource")
    @Override
    public DataSource dataSource() {
        return super.dataSource();
    }

    @Bean(name = "mariaDbJpaVendorAdapter")
    @Override
    public HibernateJpaVendorAdapter jpaVendorAdapter() {
        return super.jpaVendorAdapter();
    }
}
