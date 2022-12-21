package org.generation.italy.demo.pojo;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table
public class Photo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull(message = "no null title")
	@Column(unique = true)
	private String title;
	
	@Lob
	private String description;
	
	@NotNull(message = "no null url")
	@Column(unique = true)
	private String url;
	
	@Lob
	private String tag;
	
	@NotNull
	private boolean visible;
	
	@ManyToMany
	private List<Category> categories;
	
	public Photo() {}
	public Photo(String title, String description, String url, String tag, boolean visible) {
		
		setTitle(title);
		setDescription(description);
		setUrl(url);
		setTag(tag);
		setVisible(visible);
	}
	
	public Photo(String title, String description, String url, String tag, boolean visible, List<Category> categories) {
		
		this(title, description, url, tag, visible);
		setCategories(categories);
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public List<Category> getCategories() {
		return categories;
	}
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
    public void addCategory(Category category) {
		
		boolean finded = false;
		for (Category c : getCategories()) {
			
			if (c.getId() == category.getId())
				finded = true;
		}
		
		if (!finded)
			getCategories().add(category);
	}
	
	@Override
	public String toString() {
		
		return getId() + " - " + getTitle()
			+ "\nDescrizione: " + getDescription()
			+ "\nUrl: " + getUrl()
			+ "\ntag: " + isVisible();
	}

}
