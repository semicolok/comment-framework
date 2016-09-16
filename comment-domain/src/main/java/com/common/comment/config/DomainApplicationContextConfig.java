package com.common.comment.config;

import com.common.comment.domain.CommentDomains;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

@Configuration
@EnableTransactionManagement
@Import(value = { CommentPropertiesConfiguration.class })
@ComponentScan(basePackageClasses = { CommentDomains.class })
public class DomainApplicationContextConfig implements TransactionManagementConfigurer {

	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return null;
	}
}
