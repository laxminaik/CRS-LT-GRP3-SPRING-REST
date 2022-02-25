/**
 * 
 */
package com.lt.restController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ValidationException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.core.MediaType;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lt.bean.Course;
import com.lt.bean.Grade;
import com.lt.bean.Notification;
import com.lt.bean.Grade;
import com.lt.constant.PaymentModeConstant;
import com.lt.constant.PaymentModeConstant;
import com.lt.exception.CourseAlreadyRegisteredException;
import com.lt.exception.CourseLimitExceededException;
import com.lt.exception.CourseNotFoundException;
import com.lt.exception.SeatNotAvailableException;
import com.lt.service.ProfessorInterface;
import com.lt.service.ProfessorOperation;
import com.lt.service.RegistrationInterface;
import com.lt.service.RegistrationOperation;
import com.lt.validator.StudentValidator;



@RestController
@CrossOrigin
public class StudentCRSRestController {
	@Autowired
	private RegistrationOperation registrationInterface;// = RegistrationOperation.getInstance();
	private ProfessorOperation professorInterface;// = ProfessorOperation.getInstance();
	
	ObjectMapper mapper = new ObjectMapper();
	

	
	
	private static Logger logger = Logger.getLogger(StudentCRSRestController.class);
	
	/**
	 * Method to handle API request for course registration
	 * @param courseList
	 * @param studentId
	 * @throws ValidationException
	 * @return
	 * @throws SQLException 
	 */
	
	@RequestMapping(method=RequestMethod.PUT, value ="/student/registerCourses/{c1}/{c2}/{c3}/{c4}/{c5}/{c6}/{studentId}")
	@ResponseBody
	public ResponseEntity registerCourses(
			@PathVariable("c1") String c1,
			@PathVariable("c2") String c2,
			@PathVariable("c3") String c3,
			@PathVariable("c4") String c4,
			@PathVariable("c5") String c5,
			@PathVariable("c6") String c6,
		    @PathVariable("studentId") String studentId)	throws ValidationException, SQLException{
						
		try
		{
			List<Course> availableCourseList = registrationInterface.viewCourses(studentId);
			List<String> courseList = new ArrayList<String>();
			
			courseList.add(c1);
			courseList.add(c2);
			courseList.add(c3);
			courseList.add(c4);
			courseList.add(c5);
			courseList.add(c6);
			
			Set<String> hash_set = new HashSet<String>();
			
			for(String courseCode:courseList) {
				registrationInterface.checkCourse(courseCode, studentId, availableCourseList);	
				
					if(!hash_set.add(courseCode))
						return new ResponseEntity( "Something went wrong, Please Try Again ! ", HttpStatus.NOT_FOUND);
			}

			for(String courseCode:courseList)
				registrationInterface.addCourse(courseCode, studentId);
			
			registrationInterface.setRegistrationStatus(studentId);
		}
		catch (CourseLimitExceededException | SeatNotAvailableException | CourseNotFoundException | CourseAlreadyRegisteredException e) 
		{
			logger.info(e.getMessage());
			return new ResponseEntity( "Something went wrong, Please Try Again ! ", HttpStatus.NOT_FOUND);
		}
					
		return new ResponseEntity( "Registration Successful", HttpStatus.NOT_FOUND);
		
		
	}
	


	/**
	 * Handles api request to add a course
	 * @param courseCode
	 * @param studentId
	 * @return
	 * @throws ValidationException
	 * @throws SQLException 
	 */
	@RequestMapping(method=RequestMethod.PUT, value ="/student/addCourse/{courseCode}/{studentId}")
	@ResponseBody
	
	public ResponseEntity addCourse(
			
			
			 @PathVariable("courseCode") String courseCode,
			
			
			 @PathVariable("studentId") String studentId) throws ValidationException, SQLException{
		

		if(registrationInterface.getRegistrationStatus(studentId) == false)
		{
		return new ResponseEntity("Student course registration is pending", HttpStatus.NOT_FOUND);
		}
		else {
			
		
		List<Course> availCourseList = registrationInterface.viewCourses(studentId);
		//registrationInterface.checkCourse(courseCode, studentId, availCourseList);	
		registrationInterface.addCourse(courseCode, studentId);
		
		return new ResponseEntity( "You have successfully added Course : " ,HttpStatus.NOT_FOUND);
		}

	}

	
	/**
	 * Handles API request to drop a course
	 * @param courseCode
	 * @param studentId
	 * @return
	 * @throws ValidationException
	 * @throws SQLException 
	 */
	@RequestMapping(method=RequestMethod.DELETE, value ="/student/dropCourse/{courseCode}/{studentId}")
	@ResponseBody
	public ResponseEntity dropCourse(
			
			
			 @PathVariable("courseCode") String courseCode,
			
			
			 @PathVariable("studentId") String studentId) throws ValidationException, SQLException{
		
		

		if(registrationInterface.getRegistrationStatus(studentId) == false)

		return new ResponseEntity( "Student course registration is pending" ,HttpStatus.NOT_FOUND);
		
		try{
			
			List<Course>registeredCourseList = registrationInterface.viewRegisteredCourses(studentId);
			registrationInterface.dropCourse(courseCode, studentId, registeredCourseList);
	
			return new ResponseEntity( "You have successfully dropped Course : " ,HttpStatus.NOT_FOUND);
		}
		catch(CourseNotFoundException e)
		{	
			logger.info(e.getMessage());
			return new ResponseEntity( "You have not dropped Course : " ,HttpStatus.NOT_FOUND);
		} 
		
	}
	
	
	/**
	 * Method handles API request to view the list of available courses for a student
	 * @param studentId
	 * @return
	 * @throws ValidationException
	 * @throws SQLException 
	 */
	@RequestMapping(method=RequestMethod.GET, value ="/student/viewAvailableCourses/{studentId}")
	@ResponseBody
	public List<Course> viewCourse(
			
		//	@Min(value = 1, message = "Student ID should not be less than 1")
		//	@Max(value = 9999, message = "Student ID should be less than 1000")
			@PathVariable("studentId") String studentId) throws ValidationException, SQLException{
		
		return registrationInterface.viewCourses(studentId);
		
	}
	
	/**
	 * Method handles API request to view the list of registered courses for a student
	 * @param studentId
	 * @return
	 * @throws ValidationException
	 * @throws SQLException 
	 */
	@RequestMapping(method=RequestMethod.GET, value ="/student/viewRegisteredCourses/{studentId}")
	@ResponseBody
	
	public List<Course> viewRegisteredCourse(
			
	//		@Min(value = 1, message = "Student ID should not be less than 1")
	//		@Max(value = 9999, message = "Student ID should be less than 1000")
			@PathVariable("studentId") String studentId) throws ValidationException, SQLException{
		
			return registrationInterface.viewRegisteredCourses(studentId);
	}
	
	/**
	 * Method handles API request to make payment for registered courses
	 * @param studentId
	 * @param PaymentModeConstant
	 * @return
	 * @throws ValidationException
	 */
	@RequestMapping(method=RequestMethod.GET, value ="/make_payment/{studentId}/{PaymentModeConstant}")
	@ResponseBody
	
	public ResponseEntity make_payment(
			
			
			@PathVariable("studentId") String studentId , 
			
			@PathVariable("PaymentModeConstant") int P) throws ValidationException{
		
			double fee = 1000;
			
			logger.info("Your total fee  = " + fee);
			PaymentModeConstant mode = PaymentModeConstant.getPaymentMode(P);
			
		
//			Notification notify = null;
//			try {
//				notify = registrationInterface.payFee(studentId, mode,fee);
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}

			
//			logger.info("Your Payment is successful");
//			logger.info("Your transaction id : " + notify.getReferID());
			
			
			return new ResponseEntity(fee + "\n"+"Your Payment is successful\n" ,HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Method handles request to display the total fees for student
	 * @param studentId
	 * @return
	 * @throws ValidationException
	 * @throws SQLException 
	 */
	@RequestMapping(method=RequestMethod.GET, value ="/student/calculateFees/{studentId}")
	@ResponseBody
	
	public ResponseEntity calculateFee(
			
			@Min(value = 1, message = "Student ID should not be less than 1")
			@Max(value = 9999, message = "Student ID should be less than 1000")
			@PathVariable("studentId") String studentId) throws ValidationException, SQLException{
		
			double fee = registrationInterface.calculateFee(studentId);
			return new ResponseEntity("Your total fee  = " + fee + "\n" ,HttpStatus.NOT_FOUND);
			
	}
	
	/**
	 * Method handles request to display the grade card for student
	 * @param studentId
	 * @return
	 * @throws ValidationException
	 * @throws SQLException 
	 */
	
	@RequestMapping(method=RequestMethod.GET, value ="/student/viewGradeCard/{studentId}")
	@ResponseBody
	public List<Grade> viewGradeCard(
			
		//	@Min(value = 1, message = "Student ID should not be less than 1")
		//	@Max(value = 9999, message = "Student ID should be less than 1000")
			@PathVariable("studentId") String studentId) throws ValidationException, SQLException{
		
		
			List<Grade> grade_card = registrationInterface.viewGradeCard(studentId);
			return grade_card;
		
		
	}
	
}