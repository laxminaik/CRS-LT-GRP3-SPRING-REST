/**
 * 
 */
package com.lt.restController;

import java.util.List;


import org.apache.log4j.Logger;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.RestController;
import com.lt.bean.Course;
import com.lt.bean.Professor;
import com.lt.bean.Student;
import com.lt.exception.CourseExistsAlreadyException;
//import com.lt.exception.CourseFoundException;
import com.lt.exception.CourseNotFoundException;
import com.lt.exception.UserIdAlreadyInUseException;
import com.lt.exception.ProfessorNotAddedException;
import com.lt.exception.StudentNotFoundForApprovalException;
import com.lt.exception.UserNotFoundException;
import com.lt.service.AdminInterface;
import com.lt.service.AdminOperation;

@RestController
@CrossOrigin
public class AdminCRSRestContrlloer {
	@Autowired
	private AdminOperation adminOperation ;//= AdminOperation.getInstance();
	
	@RequestMapping(method=RequestMethod.POST, value = "/admin/assignCourseToProfessor/{courseCode}/{professorId}")
	@ResponseBody
	
	public ResponseEntity assignCourseToProfessor (@PathVariable("courseCode") String courseCode,@PathVariable("professorId") String  professorId) throws ValidationException {
		
			try {
				
				adminOperation.assignCourse(courseCode, professorId);
//				return new ResponseEntity("courseCode: " + courseCode + " assigned to professor: " + professorId).build();
				return new ResponseEntity("courseCode: " + courseCode + " assigned to professor: " + professorId, HttpStatus.NOT_FOUND);
				
			} catch (CourseNotFoundException | UserNotFoundException e) {
				
//				return Response.status(409).entity(e.getMessage()).build();
				return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
				
			}
	}
	

	
	@RequestMapping(method=RequestMethod.POST, value = "/admin/addProfessor")
	@ResponseBody

	public ResponseEntity addProfessor(@PathVariable("professor")Professor professor) throws ValidationException{
		 
		try {
			
			adminOperation.addProfessor(professor);
//			
			return new ResponseEntity("Professor with professorId: " + professor.getUserId() + " added", HttpStatus.NOT_FOUND);
			
		} catch (ProfessorNotAddedException | UserIdAlreadyInUseException e) {
			
//			return Response.status(409).entity(e.getMessage()).build();
			return new ResponseEntity((e.getMessage()), HttpStatus.NOT_FOUND);
			
		}
		
	}
	
	
	@RequestMapping(method=RequestMethod.GET, value = "/admin/viewPendingAdmissions")
	@ResponseBody

	public List<Student> viewPendingAdmissions() {
		
		return adminOperation.viewPendingAdmissions();
		
	}
	

	@RequestMapping(method=RequestMethod.PUT, value = "/admin/approveStudent/{studentId}")
	@ResponseBody
	
	public ResponseEntity approveStudent(
			
			@NonNull
			@PathParam("studentId") String studentId) throws ValidationException{
		List<Student> studentList = adminOperation.viewPendingAdmissions();
		
		try {
			
			adminOperation.approveStudent(studentId, studentList);
//			return Response.status(201).entity("Student with studentId: " + studentId + " approved").build();
			return new ResponseEntity("Student with studentId: " + studentId + " approved", HttpStatus.NOT_FOUND);
		
		} catch (StudentNotFoundForApprovalException e) {
			
//			return Response.status(409).entity(e.getMessage()).build();
			return new ResponseEntity(e.getMessage(), HttpStatus.OK);
		
		}
		
	}
	

	@RequestMapping(method=RequestMethod.GET, value = "/admin/viewCoursesInCatalogue")
	@ResponseBody
	
	public List<Course> viewCoursesInCatalogue() {
		
		System.out.println("entered view in the course catalogue!!");
		return adminOperation.viewCourses();
		//System.out.println("Courses present in the course catalogue!!");
	}
	
	@RequestMapping(method=RequestMethod.PUT, value = "/admin/deleteCourse/{courseCode}")
	@ResponseBody
	
	public ResponseEntity deleteCourse(
			//@Size(min = 4 , max = 10 , message = "Course Code length should be between 4 and 10 character")
			@NotNull
			@PathParam("courseCode") String courseCode) throws ValidationException{
		List<Course> courseList = adminOperation.viewCourses();
		
		try {
			
			adminOperation.deleteCourse(courseCode, courseList);

			return new ResponseEntity("Course with courseCode: " + courseCode + " deleted from catalog", HttpStatus.NOT_FOUND);
		
		} catch (CourseNotFoundException e) {
			
			
			return new ResponseEntity(e.getMessage(), HttpStatus.OK);
		
		}	
	}
	

	@RequestMapping(method=RequestMethod.POST, value = "/admin/addCourse")
	@ResponseBody

	 
	
	public ResponseEntity addCourse(@Valid Course course) throws ValidationException, CourseNotFoundException, CourseExistsAlreadyException{
		List<Course> courseList = adminOperation.viewCourses();
		
		adminOperation.addCourse(course, courseList);
		
		return new ResponseEntity("Course with courseCode: " + course.getCourseCode() + " added to catalog", HttpStatus.NOT_FOUND);
			
	}
}