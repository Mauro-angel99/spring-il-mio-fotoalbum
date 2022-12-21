package org.generation.italy.demo;



import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.generation.italy.demo.pojo.Category;
import org.generation.italy.demo.pojo.Photo;
import org.generation.italy.demo.pojo.Role;
import org.generation.italy.demo.pojo.User;
import org.generation.italy.demo.serv.CategoryService;
import org.generation.italy.demo.serv.PhotoService;
import org.generation.italy.demo.serv.RoleServ;
import org.generation.italy.demo.serv.UserServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SpringIlMioFotoalbumApplication implements CommandLineRunner {
	
	@Autowired
	private PhotoService photoService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private RoleServ roleServ;
	
	@Autowired
	private UserServ userServ;
	

	public static void main(String[] args) {
		SpringApplication.run(SpringIlMioFotoalbumApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		Category c1 = new Category("category1");
		Category c2 = new Category("category2");
		Category c3 = new Category("category3");
		Category c4 = new Category("category4");
		
		categoryService.save(c1);
		categoryService.save(c2);
		categoryService.save(c3);
		categoryService.save(c4);
		
	    List<Category> cp1 = Arrays.asList(new Category[] {
	    	c1, c2	
	    });
		Photo p1 = new Photo("Tokyo", "desc photo 1", "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/1b/4b/5d/10/caption.jpg?w=500&h=400&s=1&cx=1005&cy=690&chk=v1_2ed86f729380ea073850", "tag 1", true, cp1);
		Photo p2 = new Photo("New York", "desc photo 2", "https://info-viaggio.it/wp-content/uploads/2018/11/new-york-5-giorni.jpg", "tag 2", false);
		List<Category> cp3 = Arrays.asList(new Category[] {
		    	c3, c4	
		    });
		Photo p3 = new Photo("Roma", "desc photo 3", "https://www.turismoroma.it/sites/default/files/Roma%20in%20breve.jpg", "tag 3", true, cp3);
		Photo p4 = new Photo("Parigi", "desc photo 4", "https://www.marcotogni.it/top-v2/13/quanti-giorni-parigi.jpg", "tag 4", false);
		
		photoService.save(p1);
		photoService.save(p2);
		photoService.save(p3);
		photoService.save(p4);
		
		Role roleUser = new Role("USER");
		Role roleAdmin = new Role("ADMIN");
		
		roleServ.save(roleUser);
		roleServ.save(roleAdmin);
		
		User uUser = new User("user", "{noop}user", roleUser);
		User uAdmin = new User("admin", "{noop}adminpws", roleAdmin);
		
		Set<Role> godRoles = new HashSet<>();
		godRoles.add(roleUser);
		godRoles.add(roleAdmin);
		User uGod = new User("god", "{noop}god", godRoles);
		
		userServ.save(uUser);
		userServ.save(uAdmin);
		userServ.save(uGod);
	}

}
