package com.lcwd.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcwd.blog.entities.Comment;
import com.lcwd.blog.entities.Post;
import com.lcwd.blog.exceptions.ResourceNotFoundException;
import com.lcwd.blog.payloads.CommentDto;
import com.lcwd.blog.repositories.CommentRepo;
import com.lcwd.blog.repositories.PostRepo;
import com.lcwd.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
	
		Comment comment = modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		
		return modelMapper.map(commentRepo.save(comment),CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "commentId", commentId));
		commentRepo.delete(comment);
	}

}
