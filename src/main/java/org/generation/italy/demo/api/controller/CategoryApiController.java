package org.generation.italy.demo.api.controller;

import java.util.List;

import org.generation.italy.demo.pojo.Category;
import org.generation.italy.demo.pojo.Photo;
import org.generation.italy.demo.serv.CategoryService;
import org.generation.italy.demo.serv.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/1/category")
public class CategoryApiController {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private PhotoService photoService;
	
	@GetMapping("/all")
	public List<Category> getCategories(){
		
		List<Category> categories = categoryService.findAll();
		
		return categories;
	}
	
	@GetMapping("/by/photo/{id}")
	public List<Category> getCategoriesByPhotoId(
			@PathVariable("id") int id
	) {
		
		Photo photo = photoService.findPhotoById(id).get();
		
		return photo.getCategories();
	}
	

}
