package com.lcwd.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.lcwd.blog.entities.Category;
import com.lcwd.blog.entities.Post;
import com.lcwd.blog.entities.User;
import com.lcwd.blog.exceptions.ResourceNotFoundException;
import com.lcwd.blog.payloads.PostDto;
import com.lcwd.blog.payloads.PostResponse;
import com.lcwd.blog.repositories.CategoryRepo;
import com.lcwd.blog.repositories.PostRepo;
import com.lcwd.blog.repositories.UserRepo;
import com.lcwd.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		
		User user = userRepo.findById(userId).orElseThrow((()->new ResourceNotFoundException("User"," Id ", userId)));
		Category category = categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Category Id", categoryId));

		
		Post post = modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setCreatedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		return modelMapper.map(postRepo.save(post), PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		return modelMapper.map(postRepo.save(post), PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
		postRepo.delete(post);
	}

	@Override
	public PostDto getPostById(Integer postId) {
		return modelMapper.map(postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId)),PostDto.class);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
				
		Pageable p = PageRequest.of(pageNumber, pageSize, Sort.by(Direction.valueOf(sortDir.toUpperCase()), sortBy));
		
		Page<Post> pagePost = postRepo.findAll(p);
		List<Post> posts = pagePost.getContent();
		
//		List<Post> posts = postRepo.findAll();
//		return posts.stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
	
		List<PostDto> postDtos = posts.stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList()); 
	
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotolElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public PostResponse getPostByCategory(Integer categoryId, Integer pageNumber, Integer pageSize) {
//		List<Post> posts = postRepo.findByCategory(categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryId)));
//		return 
		
		Pageable p = PageRequest.of(pageNumber, pageSize);
		Page<Post> posts = postRepo.findAll(p);
		List<PostDto> postDtos = posts.stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotolElements(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLastPage(posts.isLast());
		
		return postResponse;
	}

	@Override
	public PostResponse getPostByUser(Integer userId, Integer pageNumber, Integer pageSize) {
//		List<Post> posts = postRepo.findByUser(userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "userId", userId)));
//		return posts.stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

		Pageable p = PageRequest.of(pageNumber, pageSize);
		Page<Post> posts = postRepo.findAll(p);
		List<PostDto> postDtos = posts.stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotolElements(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLastPage(posts.isLast());
		
		return postResponse;

	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = postRepo.findByTitleContaining(keyword);
		return posts.stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
	}

	
}
