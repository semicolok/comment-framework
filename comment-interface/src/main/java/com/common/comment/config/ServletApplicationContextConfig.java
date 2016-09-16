package com.common.comment.config;

import com.common.comment.config.interceptor.CommentServiceTypeInterceptor;
import com.common.comment.controller.CommentControllers;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.Charset;
import java.util.List;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackageClasses = { CommentControllers.class })
public class ServletApplicationContextConfig extends WebMvcConfigurationSupport {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(true)
				.useJaf(false)
				.ignoreAcceptHeader(false)
				.mediaType("json", MediaType.APPLICATION_JSON)
				.mediaType("xml", MediaType.APPLICATION_XML)
				.defaultContentType(MediaType.APPLICATION_JSON);
	}

	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(commentServiceTypeInterceptor());

		super.addInterceptors(registry);
	}

	@Bean
	public CommentServiceTypeInterceptor commentServiceTypeInterceptor() {
		return new CommentServiceTypeInterceptor();
	}

	@Override
	protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(getPageableArgumentResolver());

		super.addArgumentResolvers(argumentResolvers);
	}

	private PageableHandlerMethodArgumentResolver getPageableArgumentResolver() {
		PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
		resolver.setFallbackPageable(new PageRequest(0, 20));
		return resolver;
	}

	@Override
	protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(getMappingJackson2HttpMessageConverter());
		converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		converters.add(createXmlHttpMessageConverter());

		super.configureMessageConverters(converters);
	}

	private MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
		final MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		List<MediaType> mediaTypes = Lists.newArrayList(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN);
		mappingJackson2HttpMessageConverter.setSupportedMediaTypes(mediaTypes);

		return mappingJackson2HttpMessageConverter;
	}

	private HttpMessageConverter<Object> createXmlHttpMessageConverter() {
		MarshallingHttpMessageConverter xmlConverter = new MarshallingHttpMessageConverter();
		XStreamMarshaller xstreamMarshaller = new XStreamMarshaller();
		xmlConverter.setMarshaller(xstreamMarshaller);
		xmlConverter.setUnmarshaller(xstreamMarshaller);
		return xmlConverter;
	}

}
