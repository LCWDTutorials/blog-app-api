package com.lcwd.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lcwd.blog.payloads.ApiResponse;
import com.lcwd.blog.payloads.PostDto;
import com.lcwd.blog.payloads.PostResponse;
import com.lcwd.blog.services.FileService;
import com.lcwd.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/users/{userId}/categories/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
			@PathVariable Integer userId, @PathVariable Integer categoryId){
		
		PostDto post = postService.createPost(postDto, userId, categoryId);
		
		return new ResponseEntity<PostDto>(post, HttpStatus.CREATED);
		
	}
	
	@GetMapping("/users/{userId}/posts")
	public ResponseEntity<PostResponse> getPostsByUser(@PathVariable Integer userId,
			@RequestParam(value="pageNumber",defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value="pageSize", defaultValue = "5", required = false) Integer pageSize
			){
		return ResponseEntity.ok(postService.getPostByUser(userId, pageNumber, pageSize));
	}
	
	@GetMapping("/categories/{categoryId}/posts")
	public ResponseEntity<PostResponse> getPostsByCategory(@PathVariable Integer categoryId,
			@RequestParam(value="pageNumber",defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value="pageSize", defaultValue = "5", required = false) Integer pageSize
			){
		return ResponseEntity.ok(postService.getPostByCategory(categoryId, pageNumber, pageSize));
	}
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		return ResponseEntity.ok(postService.getPostById(postId));
	}
	
	/*
	 * @GetMapping("/posts") public ResponseEntity<List<PostDto>> getAllPosts(
	 * 
	 * @RequestParam(value="pageNumber", defaultValue="0", required = false) Integer
	 * pageNumber,
	 * 
	 * @RequestParam(value="pageSize", defaultValue="10", required = false) Integer
	 * pageSize ){ return ResponseEntity.ok(postService.getAllPost(pageNumber,
	 * pageSize)); }
	 */	
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value="pageNumber", defaultValue="0", required = false) Integer pageNumber,
			@RequestParam(value="pageSize", defaultValue="10", required = false) Integer pageSize,
			@RequestParam(value="sortBy", defaultValue="postId", required = false) String sortBy,
			@RequestParam(value="sortDir", defaultValue="ASC", required = false) String sortDir
	){
		return ResponseEntity.ok(postService.getAllPost(pageNumber, pageSize, sortBy, sortDir));
	}
	
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
		postService.deletePost(postId);
		return ResponseEntity.ok(new ApiResponse("Post deleted successfully!!", true));
	}
	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId){
		return ResponseEntity.ok(postService.updatePost(postDto, postId));
	}
	
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable@Length(min = 3) String keyword){
		return ResponseEntity.ok(postService.searchPosts(keyword));
	}
	
	@PostMapping("/posts/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage (
			@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId
			) throws IOException {
		
		PostDto postDto = postService.getPostById(postId);
		
		String fileName = fileService.uploadImage(path, image);
		postDto.setImageName(fileName);
		
		PostDto updatedPost = postService.updatePost(postDto, postId);
		
		return ResponseEntity.ok(updatedPost);
	}
	
	@GetMapping(value = "/posts/image/{imageName}", produces = 
			MediaType.IMAGE_PNG_VALUE)
	public void downloadImage(
			@PathVariable("imageName") String imageName,
			HttpServletResponse response
			) throws IOException {
		
		InputStream resource = fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_PNG_VALUE);		
		StreamUtils.copy(resource, response.getOutputStream());
		
	}
	
}
