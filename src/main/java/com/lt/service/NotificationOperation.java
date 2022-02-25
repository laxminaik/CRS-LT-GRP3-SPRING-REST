/**
 * 
 */
package com.lt.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lt.constant.NotificationTypeConstant;
import com.lt.constant.PaymentModeConstant;
import com.lt.dao.NotificationDaoInterface;
import com.lt.dao.NotificationDaoOperation;
@Service
public class NotificationOperation implements NotificationInterface {

	@Autowired
	private NotificationDaoOperation instance;


	@Override
	public int sendNotification(NotificationTypeConstant type, int studentId,
			PaymentModeConstant modeOfPayment, double amount) {
		return 0;

	}

	@Override
	public int sendNotification(NotificationTypeConstant type,
			String studentId, PaymentModeConstant modeOfPayment, double amount) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public UUID getReferenceId(int notificationId) {
		// TODO Auto-generated method stub
		return null;
	}

}