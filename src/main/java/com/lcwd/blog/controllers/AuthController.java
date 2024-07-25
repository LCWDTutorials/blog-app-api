package com.lcwd.blog.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lcwd.blog.entities.User;
import com.lcwd.blog.payloads.JwtAuthRequest;
import com.lcwd.blog.payloads.JwtAuthResponse;
import com.lcwd.blog.payloads.UserDto;
import com.lcwd.blog.security.CustomUserDetailsService;
import com.lcwd.blog.security.JwtHelper;
import com.lcwd.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin("*")
public class AuthController {

	@Autowired
	private JwtHelper jwtHelper;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(
			@RequestBody JwtAuthRequest request
			) throws Exception{
		
		authenticate(request.getUsername(),request.getPassword());
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		
		String token = jwtHelper.generateToken(userDetails);
		
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		response.setUser(modelMapper.map((User) userDetails, UserDto.class));
		
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
		
		
	}
	
	private void authenticate(String username, String password) throws Exception{
	
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
		
//		try {
			authenticationManager.authenticate(authToken);
//		}catch(DisabledException e) {
//			throw new Exception("User is diabled.");
//		}
		
		
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto){
		return new ResponseEntity<UserDto>(userService.registerNewUser(userDto), HttpStatus.CREATED);
	}
	

}
