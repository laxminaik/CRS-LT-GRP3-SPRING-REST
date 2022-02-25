/**
 * 
 */
package com.lt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lt.bean.Student;
import com.lt.configuration.SpringJDBCConfiguration;
import com.lt.constant.SQLQueriesConstant;
import com.lt.exception.StudentNotRegisteredException;
//import com.lt.utils.DBUtils;

@Repository
public class StudentDaoOperation implements StudentDaoInterface {
	
	private static volatile StudentDaoOperation instance=null;
	private static Logger logger = Logger.getLogger(StudentDaoOperation.class);
	@Autowired
	SpringJDBCConfiguration configurationJDBC;
	
	

	/**
	 * Method to add student to database
	 * @param student: student object containing all the fields
	 * @return true if student is added, else false
	 * @throws StudentNotRegisteredException
	 */
	@Override
	public String addStudent(Student student) throws StudentNotRegisteredException{
		//Connection connection=DBUtils.getConnection();
		
		
		String studentId=null;
		try
		{
			Connection connection = configurationJDBC.dataSource().getConnection();
			//open db connection
			PreparedStatement preparedStatement=connection.prepareStatement(SQLQueriesConstant.ADD_USER_QUERY);
			preparedStatement.setString(1, student.getUserId());
			preparedStatement.setString(2, student.getName());
			preparedStatement.setString(3, student.getPassword());
			preparedStatement.setString(4, student.getRole().toString());
			preparedStatement.setString(5, student.getGender().toString());
			preparedStatement.setString(6, student.getAddress());
			
			int rowsAffected=preparedStatement.executeUpdate();
			if(rowsAffected==1)
			{

				//add the student record
				//"insert into student (userId,branchName,batch,isApproved) values (?,?,?,?)";
				PreparedStatement preparedStatementStudent;
				preparedStatementStudent=connection.prepareStatement(SQLQueriesConstant.ADD_STUDENT,Statement.RETURN_GENERATED_KEYS);
				preparedStatementStudent.setString(1,student.getUserId());
				preparedStatementStudent.setString(2, student.getDepartment());
				preparedStatementStudent.setInt(3, student.getGradYear());
				//preparedStatementStudent.setBoolean(4, true);
				preparedStatementStudent.executeUpdate();
				ResultSet results=preparedStatementStudent.getGeneratedKeys();
				if(results.next())
					studentId=results.getString(1);
				
				
				
			}
			
			
		}
		catch(Exception ex)
		{
			throw new StudentNotRegisteredException(ex.getMessage());
		}
		finally
		{
			try {
				System.out.println("sfsdfsdf");
			} catch (Exception e) {
				logger.info(e.getMessage()+"SQL error");
				e.printStackTrace();
			}
		}
		return studentId;
	}
	
	/**
	 * Method to retrieve Student Id from User Id
	 * @param userId
	 * @return Student Id
	 */
	@Override
	public String getStudentId(String userId)  {
		//Connection connection=DBUtils.getConnection();
//		Connection connection = configurationJDBC.dataSource().getConnection();
		try {
			Connection connection = configurationJDBC.dataSource().getConnection();
			PreparedStatement statement = connection.prepareStatement(SQLQueriesConstant.GET_STUDENT_ID);
			statement.setString(1, userId);
			ResultSet rs = statement.executeQuery();
			
			if(rs.next())
			{
				return rs.getString("studentId");
			}
				
		}
		catch(SQLException e)
		{
			logger.error(e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * Method to check if Student is approved
	 * @param studentId
	 * @return boolean indicating if student is approved
	 */
	@Override
	public boolean isApproved(String studentId) {
		//Connection connection=DBUtils.getConnection();
//		Connection connection = configurationJDBC.dataSource().getConnection();
		try {
			Connection connection = configurationJDBC.dataSource().getConnection();
			PreparedStatement statement = connection.prepareStatement(SQLQueriesConstant.IS_APPROVED);
			statement.setString(1, studentId);
			ResultSet rs = statement.executeQuery();
			
			if(rs.next())
			{
				return rs.getBoolean("isApproved");
			}
				
		}
		catch(SQLException e)
		{
			logger.error(e.getMessage());
		}
		
		return false;
	}

}