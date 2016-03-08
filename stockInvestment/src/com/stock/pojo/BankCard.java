package com.stock.pojo;

import java.io.Serializable;

public class BankCard implements Serializable {

	private static final long serialVersionUID = -6440327354088677277L;
	
	private Integer id ;
	private String  bankcode;
	private String  bankname;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBankcode() {
		return bankcode;
	}
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	
	
}
