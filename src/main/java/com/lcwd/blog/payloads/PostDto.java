package com.lcwd.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

	private Integer postId;
	@NotNull @NotEmpty
	private String title;
	private String content;
	private String imageName;
	private Date createdDate;
	private UserDto user;
	private CategoryDto category;
	private Set<CommentDto> comments = new HashSet<>();
	
}
