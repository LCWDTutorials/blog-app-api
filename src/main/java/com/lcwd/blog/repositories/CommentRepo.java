package com.lcwd.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lcwd.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
