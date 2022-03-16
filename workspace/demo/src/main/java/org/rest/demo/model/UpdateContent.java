package org.rest.demo.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class UpdateContent {
	
	@Min(1)
	@Max(100)
	private int id;
	
	@NotEmpty
	private String content;
	
	public UpdateContent() {
		
	}
	
	public UpdateContent(int id, String content) {
		super();
		this.id = id;
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
