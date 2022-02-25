/**
 * 
 */
package com.lt.dao;

import java.util.List;

import com.lt.bean.Course;
import com.lt.bean.Professor;
import com.lt.bean.RegisteredCourse;
import com.lt.bean.Student;
import com.lt.bean.User;
import com.lt.exception.CourseExistsAlreadyException;
import com.lt.exception.CourseNotDeletedException;
import com.lt.exception.CourseNotFoundException;
import com.lt.exception.ProfessorNotAddedException;
import com.lt.exception.StudentNotFoundForApprovalException;
import com.lt.exception.UserIdAlreadyInUseException;
import com.lt.exception.UserNotAddedException;
import com.lt.exception.UserNotFoundException;


public interface AdminDaoInterface {
	
	public List<Course> viewCourses();
	public List<Professor> viewProfessors();
	
	
	/**
	 * Method to generate grade card of a Student 
	 * studentid 
	 * @return 
	 * 
	 * 
	 */
	
	public void setGeneratedReportCardTrue(String Studentid);
	
	public List<RegisteredCourse> generateGradeCard(String Studentid);
	
	/**
	 * Fetch Students yet to approved using SQL commands
	 * @return List of Students yet to approved
	 */
	public List<Student> viewPendingAdmissions();
	
	/**
	 * Method to approve a Student 
	 * studentid
	 * studentlist
	 */
	
	
	public void approveStudent(String studentid) throws StudentNotFoundForApprovalException;
	
	/**
	 * Method to add Professor to DB
	 * professor : Professor Object storing details of a professor 
	 */
	
	public void addProfessor(Professor professor) throws ProfessorNotAddedException, UserIdAlreadyInUseException;
	
	/**
	 * Method to Delete Course from Course Catalog
	 * @param courseCode
	 * @param courseList : Courses available in the catalog
	 * @throws CourseNotFoundException 
	 * @throws CourseNotDeletedException 
	 */

	public void removeCourse(String coursecode) throws CourseNotFoundException, CourseNotDeletedException;
	
	/**
	 * Method to add Course to Course Catalog
	 * @param course : Course object storing details of a course
	 * @param courseList : Courses available in the catalog
	 * @throws CourseExistsAlreadyException;
	 */
	
	public void addCourse(Course course) throws CourseExistsAlreadyException;
	
	/**
	 * Method to assign Course to a Professor
	 * @param courseCode
	 * @param professorId
	 * @throws CourseNotFoundException 
	 * @throws UserNotFoundException 
	 */
	public void assignCourse(String courseCode, String professorId) throws CourseNotFoundException, UserNotFoundException;
	
	public void addUser(User user) throws UserNotAddedException, UserIdAlreadyInUseException;
	public void deleteCourse(String courseCode) throws CourseNotFoundException;
}
