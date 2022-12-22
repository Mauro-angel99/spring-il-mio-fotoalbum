package org.generation.italy.demo.api.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.generation.italy.demo.pojo.Photo;
import org.generation.italy.demo.serv.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/1/photo")
public class PhotoApiController {
	
	@Autowired
	private PhotoService photoService;
	
	@GetMapping("/all")
	public List<Photo> getPhotos(){
		
		List<Photo> photos = photoService.findAll();
		List<Photo> photosV = new ArrayList<>(Arrays.asList());
		
		for( Photo photo : photos ) {
			if(photo.isVisible()) {
				photosV.add(photo);
			}
		}
		
		return photosV;
	}
	
}
