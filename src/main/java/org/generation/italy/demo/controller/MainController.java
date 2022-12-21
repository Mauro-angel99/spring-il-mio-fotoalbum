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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/")
public class MainController {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private PhotoService photoService;
	
	@GetMapping
	public String getHome(Model model) {
		
		List<Photo> photos = photoService.findAll();
		model.addAttribute("photos", photos);
		
		return "home";
	}
	
	@GetMapping("/home/admin")
	public String getPhotos(Model model) {
		
		List<Photo> photos = photoService.findAll();
		model.addAttribute("photos", photos);
		
		return "photo-search";
	}
	
	@GetMapping("/photo/admin/{id}")
	public String getPhoto(@PathVariable("id") int id, Model model) {
		
		Optional<Photo> optPhoto = photoService.findPhotoById(id);
		
		List<Category> categories = categoryService.findAll();
		model.addAttribute("categories", categories);
		
		if (optPhoto.isEmpty()) {
			
			System.err.println("Photo non presente con id: " + id);
		}
		
		Photo photo = optPhoto.get();
		
		model.addAttribute("photo", photo);
		
		return "photo";
	}
	
	@GetMapping("/photo/admin/create")
	public String createPhoto(Model model) {
		
		Photo photo = new Photo();
		model.addAttribute("photo", photo);
		
		List<Category> categories = categoryService.findAll();
		model.addAttribute("categories", categories);
		
		return "photo-create";
	}
	@PostMapping("/photo/admin/create")
	public String storePhoto(@Valid Photo photo, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
        if (bindingResult.hasErrors()) {
			
			System.err.println("ERROR ------------------------------------------");
			System.err.println(bindingResult.getAllErrors());
			System.err.println("------------------------------------------------");
			
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			
			return "redirect:/photo/admin/create";
		};
            
            try {
		
		         photoService.save(photo);
		    
            } catch(Exception e) {
    			
    			redirectAttributes.addFlashAttribute("catchError", e.getMessage());
    			
    			return "redirect:/photo/admin/create";
    		}     
		
		return "redirect:/home/admin";
	}
	
	@GetMapping("/photo/admin/update/{id}")
	public String editPhoto(@PathVariable("id") int id, Model model) {
		
		Optional<Photo> optPhoto = photoService.findPhotoById(id);
		Photo photo = optPhoto.get();
		model.addAttribute("photo", photo);
		
		List<Category> categories = categoryService.findAll();
		model.addAttribute("categories", categories);
		
		return "photo-update";
	}
	@PostMapping("photo/admin/update")
	public String updatePhoto(@Valid Photo photo, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
            if (bindingResult.hasErrors()) {
			
			System.err.println("ERROR ------------------------------------------");
			System.err.println(bindingResult.getAllErrors());
			System.err.println("------------------------------------------------");
			
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			
			return "redirect:/photo/admin/update";
		}
		
            try {
        		
		         photoService.save(photo);
		    
           } catch(Exception e) {
   			
   			redirectAttributes.addFlashAttribute("catchError", e.getMessage());
   			
           }
		
		return "redirect:/home/admin";
	}
	
	@GetMapping("/photo/admin/delete/{id}")
	public String deletePhoto(@PathVariable("id") int id) {
		
		Optional<Photo> optPhoto = photoService.findPhotoById(id);
		Photo photo = optPhoto.get();
		
		photoService.delete(photo);
		
		return "redirect:/home/admin";
	}
	
	@GetMapping("/photo/admin/search")
	public String searchPhoto(Model model,  
			@RequestParam(name = "query", required = false) String query) {
		
		List<Photo> photos = (query == null || query.isEmpty())
							? photoService.findAll()
							: photoService.findByTitle(query);		
		model.addAttribute("photos", photos);
		model.addAttribute("query", query);
		
		return "photo-search";
	}
	
	//--------------------------------------------------------------------------------------
	
		@GetMapping("/category")
		public String getCategory(Model model) {
			
			List<Category> category = categoryService.findAll();
			model.addAttribute("category", category);
			
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
			
			return "redirect:/category";
		}
		
		@GetMapping("/category/admin/update/{id}")
		public String updateCategory(
				@PathVariable("id") int id,
				Model model
			) {
			
			Category category = categoryService.findCategoryById(id);
			model.addAttribute("category", category);
			
			List<Photo> photo = photoService.findAll();
			model.addAttribute("photo", photo);
			
			return "category-update";
		}
		@PostMapping("/category/admin/update/")
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
			
			return "redirect:/category";
		}
		
		@GetMapping("/category/delete/{id}")
		public String deleteCategory(@PathVariable("id") int id) {
			
			Optional<Category> optCategory = Optional.ofNullable(categoryService.findCategoryById(id));
			Category category = optCategory.get();
			
			categoryService.delete(category);
			
			return "redirect:/category";
		}
}
