package com.example.paketlengkap.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "posts")
public class Post {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "title")
	private String title;

	@Column(name = "category")
	private String category;
	
	@Column(name = "excerpt")
	private String excerpt;
	
	@Column(name = "body")
	private String body;



	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

	public String getExcerpt() {
		return excerpt;
	}
	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}

	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
}

