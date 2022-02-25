package com.lt.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lt.bean.Course;
import com.lt.bean.EnrolledStudent;
import com.lt.dao.ProfessorDaoInterface;
import com.lt.dao.ProfessorDaoOperation;
import com.lt.exception.GradeNotAllotedException;

@Service
public class ProfessorOperation implements ProfessorInterface {
	@Autowired
	private ProfessorDaoOperation professorDAOInterface;
	
	@Override
	
	public boolean addGrade(String studentId,String courseCode,String grade) throws GradeNotAllotedException {
		try
		{
			professorDAOInterface.addGrade(studentId, courseCode, grade);
		}
		catch(Exception ex)
		{
			throw new GradeNotAllotedException(studentId);
		}
		return true;
	}
	
	
	/**
	 * Method to view all the enrolled students
	 * @param courseId: Course id 
	 * @return List of enrolled students
	 */
	
	@Override
	public List<EnrolledStudent> viewEnrolledStudents(String profId) throws SQLException{
		List<EnrolledStudent> enrolledStudents=new ArrayList<EnrolledStudent>();
		try
		{
			enrolledStudents=professorDAOInterface.getEnrolledStudents(profId);
		}
		catch(Exception ex)
		{
			throw ex;
		}
		return enrolledStudents;
	}

	
	/**
	 * Method to get list of all course a professor is teaching
	 * @param profId: professor id 
	 * @return List of courses the professor is teaching
	 */
	
	@Override
	public List<Course> viewCourses(String profId) throws SQLException {
		//call the DAO class
		//get the courses for the professor
		List<Course> coursesOffered=new ArrayList<Course>();
		try
		{
			coursesOffered=professorDAOInterface.getCoursesByProfessor(profId);
		}
		catch(Exception ex)
		{
			throw ex;
		}
		return coursesOffered;
	}
	
	
	@Override
	public String getProfessorById(String profId)
	{
		return professorDAOInterface.getProfessorById(profId);
	}

	
}