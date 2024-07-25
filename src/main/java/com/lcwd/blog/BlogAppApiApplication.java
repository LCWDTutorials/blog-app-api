package com.lcwd.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lcwd.blog.entities.Role;
import com.lcwd.blog.repositories.RoleRepo;

@SpringBootApplication
public class BlogAppApiApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApiApplication.class, args);
	}

	@Bean
	ModelMapper getModelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		
		System.out.println("Encoded password:"+passwordEncoder.encode("pwd"));
		
		try {
			Role role1 = new Role();
			role1.setId(1);
			role1.setName("ROLE_ADMIN");

			Role role2 = new Role();
			role2.setId(2);
			role2.setName("ROLE_NORMAL");

			List<Role> roles = List.of(role1, role2);
			List<Role> results = roleRepo.saveAll(roles);
			
			results.forEach(r->{
				System.out.println(r.getName());
			});
			
		}catch(Exception e) {
			e.getStackTrace();
		}
		
	}
}
