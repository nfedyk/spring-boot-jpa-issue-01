package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(exclude = {
		
								  DataSourceTransactionManagerAutoConfiguration.class, 
								  DataSourceAutoConfiguration.class, 
								  
								  HibernateJpaAutoConfiguration.class,
								  JpaRepositoriesAutoConfiguration.class,
								  SpringDataWebAutoConfiguration.class})
@ImportResource(locations= {"bootstrap.xml"})
public class SpringBootJpaIssue01Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJpaIssue01Application.class, args);
	}
}
