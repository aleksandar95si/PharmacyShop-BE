package fon.master.nst.shoppingcart.dto;

import java.io.Serializable;

import javax.persistence.Entity;


public class User implements Serializable {

	private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
	
}
