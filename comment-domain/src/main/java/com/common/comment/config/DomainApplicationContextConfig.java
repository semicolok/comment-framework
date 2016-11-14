package com.common.comment.config;

import com.common.comment.config.database.CommentDBConfiguration;
import com.common.comment.domain.CommentDomains;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.annotation.Resource;

@Configuration
@EnableTransactionManagement
@Import(value = { CommentPropertiesConfiguration.class, CommentDBConfiguration.class })
@ComponentScan(basePackageClasses = { CommentDomains.class })
public class DomainApplicationContextConfig implements TransactionManagementConfigurer {

	@Resource
	private PlatformTransactionManager commentTransactionManager;

	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return commentTransactionManager;
	}
}
