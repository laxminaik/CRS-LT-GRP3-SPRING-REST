package com.lt.restController;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;


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

import com.lt.bean.Student;
import com.lt.constant.GenderConstant;
import com.lt.constant.RoleConstant;
import com.lt.exception.StudentNotRegisteredException;
import com.lt.exception.UserNotFoundException;
import com.lt.service.StudentInterface;
import com.lt.service.StudentOperation;
import com.lt.service.UserInterface;
import com.lt.service.UserOperation;

@RestController
@CrossOrigin
public class UserCRSRestController {
	@Autowired
	private StudentOperation studentInterface;

	@Autowired
	private UserOperation userInterface;

	@RequestMapping(method = RequestMethod.PUT, value = "/user/updatePassword/{userId}/{newPassword}")
	@ResponseBody
	public ResponseEntity updatePassword(

			@PathVariable("userId") String userId,

			@PathVariable("newPassword") String newPassword) throws ValidationException {

		System.out.println("YES!!!");
		if (userInterface.updatePassword(userId, newPassword)) {
			//			return Response.status(201).entity("Password updated successfully! ").build();
			return new ResponseEntity("Password updated successfully! ", HttpStatus.NOT_FOUND);
		}

		//	   return Response.status(500).entity("Something went wrong, please try again!").build();
		return new ResponseEntity("Something went wrong, please try again!", HttpStatus.NOT_FOUND);

	}

	@RequestMapping(method = RequestMethod.POST, value = "/user/login/{userId}/{password}")
	@ResponseBody
	public ResponseEntity verifyCredentials(

			@PathVariable("userId") String userId,

			@PathVariable("password") String password) throws ValidationException {

		try {
			System.out.println(userId + " " + password);
			boolean loggedin = userInterface.verifyCredentials(userId, password);

			if (loggedin) {
				System.out.println("logged in");
				String role = userInterface.getRole(userId);
				System.out.println("got role");
				RoleConstant userRole = RoleConstant.stringToName(role);
				switch (userRole) {

				case STUDENT:
					String studentId = userId;
					boolean isApproved = studentInterface.isApproved(studentId);
					if (!isApproved) {
						return new ResponseEntity("Login unsuccessful! Student " + userId
								+ " has not been approved by the administration!", HttpStatus.NOT_FOUND);
						//							return Response.status(200).entity("Login unsuccessful! Student "+userId+" has not been approved by the administration!" ).build();
					}
					break;

				}
				return new ResponseEntity("Login successful as "+ userRole ,HttpStatus.NOT_FOUND);
				//					return Response.status(200).entity("Login successful").build();
			} else {
				return new ResponseEntity("Invalid credentials!", HttpStatus.NOT_FOUND);

			}
		} catch (UserNotFoundException e) {

			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/user/getRole")
	@ResponseBody
	public ResponseEntity<?> getRole(@RequestParam(value = "userId" , required = true)  String userId) throws ValidationException {
	String result ; 
	try {
		result = userInterface.getRole(userId);
	} catch (Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	}
	return ResponseEntity.status(HttpStatus.OK).body("Role Type : " + result);
}


	@RequestMapping(method = RequestMethod.POST, value = "/user/studentRegistration")
	@ResponseBody
	public ResponseEntity<?> getName(@RequestParam(value = "name" , required = true) String name,
			@RequestParam(value = "userId" , required = true)  String userId,
			@RequestParam(value = "password" , required = true) String password,
			//@RequestParam(value = "gender" , required = true) GenderConstant gender,
			@RequestParam(value = "batch" , required = true) int batch,
			@RequestParam(value = "branch" , required = true) String branch,
			@RequestParam(value = "address" , required = true) String address ) {
		String result ;
		try {


			result = studentInterface.register(name, userId, password,GenderConstant.getName(1), batch, branch, address);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}





}