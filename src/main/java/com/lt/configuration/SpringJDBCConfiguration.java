package com.lt.configuration;


//package com.lt.config;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

//import com.lt.DAO.CourseDAO;
//import com.lt.DAO.CourseDAOImpl;



@Configuration
public class SpringJDBCConfiguration {
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		//MySQL database we are using
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/crs_application");//change url
		dataSource.setUsername("root");//change userid
		dataSource.setPassword("root");//change pwd

		return dataSource;
	}



	}



