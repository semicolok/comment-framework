package com.common.comment.config.database;

import com.google.common.collect.Maps;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class CommentDBConfiguration {

	@Autowired
	private Environment environment;

	@Bean
	public DataSource routingDataSource() {
		AbstractRoutingDataSource routingDataSource = new AbstractRoutingDataSource() {
			@Override
			protected Object determineCurrentLookupKey() {
//				return databaseRoutingVariableScope().getCurrentValue();
				return null;
			}
		};
		routingDataSource.setDefaultTargetDataSource(dataSource());
		Map<Object, Object> dataSources = Maps.newHashMap();
		dataSources.put(DatabaseRouting.DATABASE_PLAIN, dataSource());
//		dataSources.put(DatabaseRouting.DATABASE_COMMENTS_SHARD, commentsDataSource());
//		dataSources.put(DatabaseRouting.DATABASE_USER_SHARD, userDataSource());
//		dataSources.put(DatabaseRouting.DATABASE_APP_SHARD, appDataSource());
		routingDataSource.setTargetDataSources(dataSources);
		return routingDataSource;
	}

	private DataSource dataSource() {
		BasicDataSource basicDataSource = buildDataSource();
		basicDataSource.setUrl(getProperty("database.comment.url", String.class));

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
