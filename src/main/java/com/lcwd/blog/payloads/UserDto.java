package com.lcwd.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	private int id;
	
	@NotNull @NotEmpty(message = "Username is required")
	@Size(min=4, message="Username must be a min of 4 chars")
	private String name;
	
	@Email(message="Email address is not valid!!") 
	@NotNull @NotEmpty(message = "Email is required")
	private String email;
	
	@NotNull @NotEmpty(message = "Password is required")
	@Size(min = 3, max = 10, message = "Enter password with min 3 chars and max 10 chars")
//	@JsonIgnore
	private String password;
	
	@NotEmpty(message = "About is required")
	private String about;
	
	private Set<RoleDto> roles = new HashSet<>();
	
	@JsonIgnore
	public String getPassword() {
		return this.password;
	}
	
	@JsonProperty
	public void setPassword(String password) {
		this.password=password;
	}

}
