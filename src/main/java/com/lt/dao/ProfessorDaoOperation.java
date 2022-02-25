package com.lt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lt.bean.Course;
import com.lt.bean.EnrolledStudent;
import com.lt.configuration.SpringJDBCConfiguration;
import com.lt.constant.SQLQueriesConstant;
//import com.lt.utils.DBUtils;

@Repository
public class ProfessorDaoOperation implements ProfessorDaoInterface {

	private static volatile ProfessorDaoOperation instance=null;
	private static Logger logger = Logger.getLogger(UserDaoOperation.class);
	@Autowired
	SpringJDBCConfiguration configurationJDBC;
	

	@Override
	public List<Course> getCoursesByProfessor(String profId) throws SQLException {
		//Connection connection=DBUtils.getConnection();
		List<Course> courseList=new ArrayList<Course>();
		Connection connection = configurationJDBC.dataSource().getConnection();
		try {
//			Connection connection = configurationJDBC.dataSource().getConnection();
			PreparedStatement statement = connection.prepareStatement(SQLQueriesConstant.GET_COURSES);
			
			statement.setString(1, profId);
			
			ResultSet results=statement.executeQuery();
			while(results.next())
			{
				courseList.add(new Course(results.getString("courseCode"),results.getString("courseName"),results.getString("professorId"),results.getInt("seats")));
			}
		}
		catch(SQLException e)
		{
			logger.error(e.getMessage());
		}
		finally
		{
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return courseList;
		
	}

	/**
	 * Method to view list of enrolled Students using SQL Commands
	 * @param: profId: professor id 
	 * @param: courseCode: course code of the professor
	 * @return: return the enrolled students for the corresponding professor and course code.
	 */
	@Override
	public List<EnrolledStudent> getEnrolledStudents(String courseId) {
		//Connection connection=DBUtils.getConnection();
//		Connection connection = configurationJDBC.dataSource().getConnection();
		List<EnrolledStudent> enrolledStudents=new ArrayList<EnrolledStudent>();
		try {
			Connection connection = configurationJDBC.dataSource().getConnection();
			PreparedStatement statement = connection.prepareStatement(SQLQueriesConstant.GET_ENROLLED_STUDENTS);
			statement.setString(1, courseId);
			
			ResultSet results = statement.executeQuery();
			while(results.next())
			{
				//public EnrolledStudent(String courseCode, String courseName, int studentId) 
				enrolledStudents.add(new EnrolledStudent(results.getString("courseCode"),results.getString("courseName"),results.getString("studentId")));
			}
		}
		catch(SQLException e)
		{
			logger.error(e.getMessage());
		}
		finally
		{
			
//				connection.close();
				System.out.println("Excuted");
			
		}
		return enrolledStudents;
	}
	
	/**
	 * Method to GradeConstant a student using SQL Commands
	 * @param: profId: professor id 
	 * @param: courseCode: course code for the corresponding 
	 * @return: returns the status after adding the grade
	 */
	public Boolean addGrade(String studentId,String courseCode,String grade) {
		//Connection connection=DBUtils.getConnection();
//		Connection connection = configurationJDBC.dataSource().getConnection();
		try {
			Connection connection = configurationJDBC.dataSource().getConnection();
			PreparedStatement statement = connection.prepareStatement(SQLQueriesConstant.ADD_GRADE);
			
			statement.setString(1, grade);
			statement.setString(2, courseCode);
			statement.setString(3, studentId);
			
			int row = statement.executeUpdate();
			
			if(row==1)
				return true;
			else
				return false;
			
//			connection.close();
		}
		catch(SQLException e)
		{
			logger.error(e.getMessage());
		}
//		finally
//		{
//			try {
//				connection.close();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		return false;
	}
	

	/**
	 * Method to Get professor name by id
	 * @pa ram profId
	 * @return Professor Id in string
	 */
	@Override
	public String getProfessorById(String profId)
	{
		String prof_Name = null;
		//Connection connection=DBUtils.getConnection();
//		Connection connection = configurationJDBC.dataSource().getConnection();
		try 
		{
			Connection connection = configurationJDBC.dataSource().getConnection();
			PreparedStatement statement = connection.prepareStatement(SQLQueriesConstant.GET_PROF_NAME);
			
			statement.setString(1, profId);
			ResultSet rs = statement.executeQuery();
			rs.next();
			
			prof_Name = rs.getString(1);
			connection.close();
			
		}
		catch(SQLException e)
		{
			logger.error(e.getMessage());
		}
//		finally
//		{
//			try 
//			{
//				connection.close();
//			} catch (SQLException e) 
//			{
//				e.printStackTrace();
//			}
//		}
		
		return prof_Name;
	}
}