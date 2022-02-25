package com.lt.bean;

import java.io.Serializable;

public class Scholarship implements Serializable{
	String name;
	int amount;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
}
