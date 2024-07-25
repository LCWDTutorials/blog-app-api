package com.lcwd.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lcwd.blog.entities.User;
import com.lcwd.blog.exceptions.ResourceNotFoundException;
import com.lcwd.blog.payloads.UserDto;
import com.lcwd.blog.repositories.RoleRepo;
import com.lcwd.blog.repositories.UserRepo;
import com.lcwd.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		return userToDto(userRepo.save(dtoToUser(userDto)));
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user = userRepo.findById(userId).orElseThrow((()->new ResourceNotFoundException("User"," Id ", userId)));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());

		User updatedUser = userRepo.save(user);
		return userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = userRepo.findById(userId).orElseThrow((()->new ResourceNotFoundException("User"," Id ", userId)));
		return userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = userRepo.findAll();
		
		return users.stream().map((user)->userToDto(user)).collect(Collectors.toList());
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = userRepo.findById(userId).orElseThrow((()->new ResourceNotFoundException("User"," Id ", userId)));
		userRepo.delete(user);
	}
	
	private UserDto userToDto(User user) {
		return modelMapper.map(user, UserDto.class);
	}

	private User dtoToUser(UserDto userDto) {
		return modelMapper.map(userDto, User.class);
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		
		User user = modelMapper.map(userDto, User.class);
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.getRoles().add(roleRepo.findById(2).get());
		
		return modelMapper.map(userRepo.save(user),UserDto.class);
	}
}
