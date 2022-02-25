package com.lt.restController;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.ValidationException;

import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lt.bean.Course;
import com.lt.bean.EnrolledStudent;
import com.lt.bean.Professor;
import com.lt.service.ProfessorInterface;
import com.lt.service.ProfessorOperation;
import com.lt.validator.ProfessorValidator;

@RestController
@CrossOrigin
public class ProfessorCRSRestController {
	@Autowired
	private ProfessorOperation professorInterface;
	
	@RequestMapping(method=RequestMethod.GET, value ="/professor/getEnrolledStudents/{profId}")
	@ResponseBody
	public List<EnrolledStudent> viewEnrolledStudents(@PathVariable("profId") String profId) throws ValidationException	{
		
		List<EnrolledStudent> students=new ArrayList<EnrolledStudent>();
		try
		{
			students=professorInterface.viewEnrolledStudents(profId);
		}
		catch(Exception ex)
		{
			return null;
		}	
		return students;
	}
	
	@RequestMapping(method=RequestMethod.GET, value ="/professor/getCourses/{profId}")
	@ResponseBody
	public List<Course> getCourses(@PathVariable("profId") String profId) throws ValidationException	{
		
		List<Course> courses=new ArrayList<Course>();
		try
		{
			courses=professorInterface.viewCourses(profId);	
		}
		catch(Exception ex)
		{
			return null;
		}
		return courses;
	
	}
	
	@RequestMapping(method=RequestMethod.PUT, value ="/professor/addGrade/{studentId}/{courseCode}/{profId}/{grade}")
	@ResponseBody
	public ResponseEntity addGrade(@PathVariable("studentId") String studentId,@PathVariable("courseCode") String courseCode,@PathVariable("profId") String profId,
			
			@PathVariable("grade") String grade) throws ValidationException  	{
		
		try
		{
			List<EnrolledStudent> enrolledStudents=new ArrayList<EnrolledStudent>();
			enrolledStudents=professorInterface.viewEnrolledStudents(profId);
			List<Course> coursesEnrolled=new ArrayList<Course>();
			coursesEnrolled	=professorInterface.viewCourses(profId);
			if(!(ProfessorValidator.isValidStudent(enrolledStudents, studentId) && ProfessorValidator.isValidCourse(coursesEnrolled, courseCode)))
			{
				professorInterface.addGrade(studentId, courseCode, grade);
			}
			else
			{
				//error code
				return new ResponseEntity( "Something went wrong, Please Try Again ! ", HttpStatus.NOT_FOUND);
			}
		}
		catch(Exception ex)
		{
			//error code
			return new ResponseEntity( "Something went wrong, Please Try Again ! ", HttpStatus.NOT_FOUND);
		}
		//error code
		return new ResponseEntity( "Something went wrong, Please Try Again ! ", HttpStatus.NOT_FOUND);
	}
	
	
}