package com.lcwd.blog.services;

import java.util.List;

import com.lcwd.blog.payloads.PostDto;
import com.lcwd.blog.payloads.PostResponse;

public interface PostService {

	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

	PostDto updatePost(PostDto postDto, Integer postId);

	void deletePost(Integer postId);
	
	PostDto getPostById(Integer postId);
	
//	List<PostDto> getAllPost(Integer pageNumber, Integer pageSize);
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
//	List<PostDto> getPostByCategory(Integer categoryId);
	PostResponse getPostByCategory(Integer categoryId, Integer pageNumber, Integer pageSize);
	
//	List<PostDto> getPostByUser(Integer userId);
	PostResponse getPostByUser(Integer userId, Integer pageNumber, Integer pageSize);
	
	List<PostDto> searchPosts(String keyword);
	
}
