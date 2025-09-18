package com.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

/**
 * <h1> Sakshi E-Classifieds </h1>
 * This EClassifiedPortal java class is used to mark a configuration class and also triggers auto-configuration and component scanning.
 * @author Sathish Babu D
 * @since 11-04-2023
 *
 */

@SpringBootApplication
@EnableScheduling
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class})
@EnableJpaRepositories(basePackages = {
		"com.portal.controller", "com.portal","com.portal.basedao","com.portal.security","com.portal.repository" })
@ComponentScan(basePackages = { "com.portal","com.portal.*","com.portal.controller","com.portal.basedao","com.portal.security","com.portal.repository" })
@PropertySource({
	  "classpath:/com/portal/messages/messages.properties",
	  "classpath:/com/portal/queries/communication_db.properties",
	  "classpath:/com/portal/queries/user_db.properties",
	  "classpath:/com/portal/queries/setting_db.properties",
	  "classpath:/com/portal/queries/org_db.properties",
//	  "classpath:/com/portal/queries/clf_db.properties",
	  "file:${catalina.home}/conf/SEC/config_${spring.profiles.active}.properties"})
public class EClassifiedPortal extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(EClassifiedPortal.class);
	}

	/**
	 * This method starts up the ApplicationContext
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(EClassifiedPortal.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("com/portal/");
		return messageSource;
	}

	/**
	 * It will return the Java mail sender object with empty configurations
	 * 
	 * @return JavaMailSender
	 */
	@Bean
	public JavaMailSender getMailSender() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		return javaMailSender;
	}

}
