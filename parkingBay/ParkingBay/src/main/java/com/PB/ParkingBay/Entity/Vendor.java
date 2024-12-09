package com.PB.ParkingBay.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Vendor")
public class Vendor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Name is mandatory")
	@Size(min = 3, message = "Name must be least 3 characters")
	private String name;
	
	@Email(message = "Email should be valid")
	@NotBlank(message = "Email is mandatory")
	private String email;
	
	@NotBlank(message = "Mobile number is mandatory")
	@Size(min = 10, max = 10, message = "Mobile number should be 10 digits")
	private String mobile;
	
	@NotBlank(message = "Password is mandatory")
	@Size(min = 8, message = "Password must have at least 8 characters")
	private String password;
	
	@Size(min = 8, message = "Password must have at least 8 characters")
	private String confirmPassword;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
}
