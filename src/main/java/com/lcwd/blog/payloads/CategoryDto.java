package com.lcwd.blog.payloads;

import org.hibernate.validator.constraints.Length;

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
public class CategoryDto {
	
	private Integer categoryId;
	
	@NotNull @NotEmpty
	@Length(min=4, message="Minimum size of category title is 4.")
	private String categoryTitle;
	
	@NotNull @NotEmpty
	@Length(min=10, max=100, message="Category Desc should be min 10 chars and max 100 chars.")
	private String categoryDescription;

}
