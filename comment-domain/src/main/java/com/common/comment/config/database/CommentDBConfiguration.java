package com.common.comment.config.database;

import com.common.comment.domain.CommentDomains;
import com.common.comment.domain.holder.CommentServiceType;
import com.common.comment.domain.holder.ContextHolder;
import com.google.common.collect.Maps;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableJpaRepositories(basePackageClasses = { CommentDomains.class },
		entityManagerFactoryRef = "commentEntityManagerFactory", transactionManagerRef = "commentTransactionManager")
public class CommentDBConfiguration {

	@Autowired
	private Environment environment;

	@Bean
	public LocalContainerEntityManagerFactoryBean commentEntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(routingDataSource());
		factoryBean.setPersistenceUnitName("commonComment");
		factoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);

		return entityManagerFactoryBean;
	}

	@Bean
	public PlatformTransactionManager commentTransactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

	@Bean
	public DataSource routingDataSource() {
		AbstractRoutingDataSource routingDataSource = new AbstractRoutingDataSource() {
			@Override
			protected Object determineCurrentLookupKey() {
				return ContextHolder.getCommentServiceType();
			}
		};

		routingDataSource.setDefaultTargetDataSource(dataSource());

		Map<Object, Object> dataSources = Maps.newHashMap();
		dataSources.put(CommentServiceType.PLAIN_BOARD, dataSource());
		dataSources.put(CommentServiceType.CHAT_BOARD, chatDataSource());

		routingDataSource.setTargetDataSources(dataSources);

		return routingDataSource;
	}

	private DataSource dataSource() {
		BasicDataSource basicDataSource = buildDataSource();
		basicDataSource.setUrl(getProperty("database.comment.url", String.class));

		return basicDataSource;
	}

	private DataSource chatDataSource() {
		BasicDataSource basicDataSource = buildDataSource();
		basicDataSource.setUrl(getProperty("database.chat.url", String.class));

		return basicDataSource;
	}

	private BasicDataSource buildDataSource() {
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(getProperty("database.driverClassName", String.class));
		basicDataSource.setUsername(getProperty("database.username", String.class));
		basicDataSource.setPassword(getProperty("database.password", String.class));

		basicDataSource.setMaxIdle(getProperty("database.maxIdle", Integer.class));
		basicDataSource.setMaxTotal(getProperty("database.maxTotal", Integer.class));
		basicDataSource.setMinIdle(getProperty("database.minIdle", Integer.class));
		basicDataSource.setTimeBetweenEvictionRunsMillis(getProperty("database.timeBetweenEvictionRunsMillis", Long.class));
		basicDataSource.setMinEvictableIdleTimeMillis(getProperty("database.minEvictableIdleTimeMillis", Long.class));

		return basicDataSource;
	}

	private <T> T getProperty(String key, Class<T> clazz) {
		return environment.getProperty(key, clazz);
	}
}
