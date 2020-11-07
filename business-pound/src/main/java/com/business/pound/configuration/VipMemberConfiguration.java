package com.business.pound.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(basePackages="com.business.pound.repository",entityManagerFactoryRef = "magicVipMemberEntityManager",transactionManagerRef="magicVipMemberTransactionManager")
@EntityScan({"com.business.pound.entity"})
public class VipMemberConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(VipMemberConfiguration.class);

    @Autowired
    private Environment env;
    @Autowired
    @Qualifier("vipmemberDataSource")
    private DataSource dataSource;
    // 配置字段
    protected Map<String, Object> jpaProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("show-sql",true);
        properties.put("hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName());
        //properties.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
        properties.put("hibernate.hbm2ddl.update",
                env.getProperty("hibernate.hbm2ddl.update"));
        properties.put("hibernate.dialect",
                env.getProperty("hibernate.dialect"));
        return properties;
    }

    @Bean("magicVipMemberEntityManager")
    public LocalContainerEntityManagerFactoryBean magicEntityManager() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan(
                new String[] { "com.business.pound.entity" });

        HibernateJpaVendorAdapter vendorAdapter
                = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        em.setJpaPropertyMap(jpaProperties());

        return em;
    }
    @Bean("magicVipMemberTransactionManager")
    @Primary
    public PlatformTransactionManager magicTransactionManager() {

        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                magicEntityManager().getObject());
        return transactionManager;
    }

    //多数源配置
    //@Bean(name = "magicGoodsTransactionManager")
    //public DataSourceTransactionManager transactionManager(@Qualifier("magicProductDataSource") DataSource dataSource) {
    //    return new DataSourceTransactionManager(dataSource);
    //}

}
