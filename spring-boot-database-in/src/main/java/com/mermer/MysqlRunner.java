package com.mermer;


import java.sql.Connection;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MysqlRunner implements ApplicationRunner{

	@Autowired
	DataSource dataSource;
	
	@Autowired
	JdbcTemplate jdbc;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Connection con =dataSource.getConnection();
		String url = con.getMetaData().getURL();
		String username = con.getMetaData().getUserName();
		
		System.out.println(url + "," + username);
		
		Statement state =  con.createStatement();
		
		
		String sql = "CREATE TABLE USER (ID INTEGER NOT NULL, name VARCHAR(255), PRIMARY KEY (id))";
		try {
			//state.execute(sql);
			System.out.println("create table");
		}catch(Exception e) {
			con.rollback();
			System.out.println("rollback complete");
		}
		con.close();
		
		//jdbc.execute("insert into user values(2, 'eunwoo')");
	}
	
	
	
}
