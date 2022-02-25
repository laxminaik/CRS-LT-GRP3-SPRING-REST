package com.lt.bean;

import java.util.Date;
import java.io.Serializable;

import com.lt.constant.GenderConstant;
import com.lt.constant.RoleConstant;

public class Admin extends User implements Serializable
{
		
		private String adminID;
	
		public Admin(String userID, String name, GenderConstant gender, RoleConstant role, String password, String address) 
		{
			super(userID, name, role, password, gender, address);
		}	

		
		
}
