package com.lt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lt.bean.SemRegistration;  
import com.lt.constant.RoleConstant;
import com.lt.dao.StudentDaoInterface;
import com.lt.dao.StudentDaoOperation;
import com.lt.exception.StudentNotRegisteredException;
import com.lt.bean.Student;
//import com.lt.client.CRSApplication;
import com.lt.constant.GenderConstant;

@Service
public class StudentOperation implements StudentInterface {
	@Autowired
	private StudentDaoOperation studentDaoInterface;
	
	@Override
	public String register(String name,String userId,String password,GenderConstant gender,int batch,String branch,String address) throws StudentNotRegisteredException{
		String studentId;
		try
		{
			//call the DAO class, and add the student record to the DB
			System.out.println("1 reg");
			Student newStudent=new Student(userId,name,RoleConstant.STUDENT,password,gender,address,branch,userId,batch,false);
			System.out.println("\nYour account has been created and pending for Approval by Admin.\n");
			studentId=studentDaoInterface.addStudent(newStudent);
			
		}
		catch(StudentNotRegisteredException ex)
		{
			throw ex;
		}
		return studentId;
	}

    /**
     * Method to get Student ID from User ID
     * @param userId
     * @return Student ID
     */
    @Override
    public String getStudentId(String userId) {
        
        return studentDaoInterface.getStudentId(userId);
    }



	
	/**
     * Method to check if student is approved by Admin or not
     * @param studentId
     * @return boolean indicating if student is approved
     */
	@Override
	public boolean isApproved(String studentId) {
		return studentDaoInterface.isApproved(studentId);
	}


}