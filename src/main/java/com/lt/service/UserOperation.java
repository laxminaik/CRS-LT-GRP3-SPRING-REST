package com.lt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lt.dao.UserDaoInterface;
import com.lt.dao.UserDaoOperation;
import com.lt.exception.UserNotFoundException;

@Service
public class UserOperation implements UserInterface {
	@Autowired
	private UserDaoOperation userDaoInterface;
	
	@Override
	public boolean updatePassword(String userID,String newPassword) {
		System.out.println("1 upd");
		return userDaoInterface.updatePassword(userID, newPassword);
	}

	
	/**
	 * Method to verify User credentials
	 * @param userID
	 * @param password
	 * @return boolean indicating if user exists in the database
	 */

	@Override
	public boolean verifyCredentials(String userID, String password) throws UserNotFoundException {
		//DAO class
		try
		{
			return userDaoInterface.verifyCredentials(userID, password);		
		}
		finally
		{
			
		}
	}
	
	/**
	 * Method to get role of a specific User
	 * @param userId
	 * @return RoleConstant of the User
	 */
	@Override
	public String getRole(String userId) {
		return userDaoInterface.getRole(userId);
	}

	


	

}