package com.lt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lt.configuration.SpringJDBCConfiguration;
import com.lt.constant.SQLQueriesConstant;
import com.lt.exception.UserNotFoundException;
//import com.lt.utils.DBUtils;

@Repository
public class UserDaoOperation implements UserDaoInterface{
	private static volatile UserDaoOperation instance=null;
	private static Logger logger = Logger.getLogger(UserDaoOperation.class);
	@Autowired
	SpringJDBCConfiguration configurationJDBC;
	

	@Override
	public boolean updatePassword(String userId, String newPassword) {
		//Connection connection=DBUtils.getConnection();
		
		try {
			Connection connection = configurationJDBC.dataSource().getConnection();
			System.out.println("2 upd");
			PreparedStatement statement = connection.prepareStatement(SQLQueriesConstant.UPDATE_PASSWORD);
			
			statement.setString(1, newPassword);
			statement.setString(2, userId);
			
			int row = statement.executeUpdate();
			
			if(row==1)
				return true;
			else
				return false;
		}
		catch(SQLException e)
		{
			logger.error(e.getMessage());
		}
		finally
		{
			System.out.println("gsgsgs");
		}
		return false;
	}
	
	/**
	 * Method to verify credentials of Users from DataBase
	 * @param userId
	 * @param password
	 * @return Verify credentials operation status
	 * @throws UserNotFoundException
	 */
	@Override
	public boolean verifyCredentials(String userId, String password) throws UserNotFoundException {
		//Connection connection = DBUtils.getConnection();
//		Connection connection = configurationJDBC.dataSource().getConnection();
		System.out.println("Database connected");
		try
		{
			Connection connection = configurationJDBC.dataSource().getConnection();
			//open db connection
			
			PreparedStatement preparedStatement=connection.prepareStatement(SQLQueriesConstant.VERIFY_CREDENTIALS);
			preparedStatement.setString(1,userId);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			System.out.println("inside verify");
			
			if(!resultSet.next())
				throw new UserNotFoundException(userId);

			else if(password.equals(resultSet.getString("password")))
			{
				System.out.println("inside equals");
				return true;
			}
			else
			{
				return false;
			}
			
		}
		catch(SQLException ex)
		{
			logger.error("Something went wrong, please try again! "+ ex.getMessage());
		}
		finally
		{
			System.out.println("fgdfgfdgdfg");
		}
		return false;
	}

	/**
	 * Method to update password of user in DataBase
	 * @param userID
	 * @return Update Password operation Status
	 */
	@Override
	public boolean updatePassword(String userID) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Method to get RoleConstant of User from DataBase
	 * @param userId
	 * @return RoleConstant
	 */
	@Override
	public String getRole(String userId)
	{
		//Connection connection=DBUtils.getConnection();
//		Connection connection = configurationJDBC.dataSource().getConnection();
		try {
			Connection connection = configurationJDBC.dataSource().getConnection();
			logger.info(userId);
			//connection=DBUtils.getConnection();
			System.out.println(connection.isClosed());
			PreparedStatement statement = connection.prepareStatement(SQLQueriesConstant.GET_ROLE);
			statement.setString(1, userId);
			ResultSet rs = statement.executeQuery();
			
			
			
			logger.info("query executed");
			
			if(rs.next())
			{
				logger.info(rs.getString("role"));
				return rs.getString("role");
			}
				
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
			
		}
		
		finally
		{
			System.out.println("gddhdhgd");
		}
		return null;
	}

	
}