package org.generation.italy.demo.controller;

import java.util.List;
import java.util.Optional;

import org.generation.italy.demo.pojo.Category;
import org.generation.italy.demo.pojo.Photo;
import org.generation.italy.demo.serv.CategoryService;
import org.generation.italy.demo.serv.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/category/admin")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private PhotoService photoService;

	@GetMapping
	public String getCategory(Model model) {
		
		List<Category> categories = categoryService.findAll();
		model.addAttribute("categories", categories);
		
		return "categories";
	}
	@GetMapping("/category/admin/create")
	public String createCategory(Model model) {
		
		Category category = new Category();
		model.addAttribute("category", category);
		
		List<Photo> photos = photoService.findAll();
		model.addAttribute("photos", photos);
		
		return "category-create";
	}
	@PostMapping("/category/admin/create")
	public String storeCategory(@Valid Category category) {
		
		for (Photo p : category.getPhotos()) {
			
			p.getCategories().add(category);
		}
		
		categoryService.save(category);
		
		return "redirect:/category/admin";
	}
	
	@GetMapping("/category/admin/update/{id}")
	public String updateCategory(
			@PathVariable("id") int id,
			Model model
		) {
		
		Category category = categoryService.findCategoryById(id);
		model.addAttribute("category", category);
		
		List<Photo> photos = photoService.findAll();
		model.addAttribute("photos", photos);
		
		return "category-update";
	}
	@PostMapping("/category/admin/update/{id}")
	public String editCategory(
			@PathVariable("id") int id,
			@Valid Category category
		) {
		
		Category oldC = categoryService.findCategoryById(id);
		
		for (Photo p : oldC.getPhotos()) {
			
			p.getCategories().remove(oldC);
		}
		
		for (Photo p : category.getPhotos()) {
			
			p.addCategory(category);
		}
		
		categoryService.save(category);
		
		return "redirect:/category/admin";
	}
	
	@GetMapping("/category/admin/delete/{id}")
	public String deleteCategory(@PathVariable("id") int id) {
		
		Optional<Category> optCategory = Optional.ofNullable(categoryService.findCategoryById(id));
		Category category = optCategory.get();
		
		categoryService.delete(category);
		
		return "redirect:/category/admin";
	}
}