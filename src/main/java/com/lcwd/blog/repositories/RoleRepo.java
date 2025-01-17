package com.lcwd.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lcwd.blog.entities.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {

}
