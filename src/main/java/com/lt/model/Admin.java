package com.lt.model;

import java.util.Date;

import com.lt.constant.GenderConstant;
import com.lt.constant.RoleConstant;

public class Admin extends User
{
		
		private String adminID;
	
		public Admin(String userID, String name, GenderConstant gender, RoleConstant role, String password, String address) 
		{
			super(userID, name, role, password, gender, address);
		}	

		
		
}
