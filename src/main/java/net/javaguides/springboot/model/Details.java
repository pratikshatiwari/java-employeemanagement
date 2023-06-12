package net.javaguides.springboot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Details {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "first_name")
	private String FirstName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return FirstName;
	}

	public void setName(String name) {
		this.FirstName = name;
	}

	public Details(long id, String name) {
		super();
		this.id = id;
		this.FirstName = name;
	}
	

	public Details() {
		super();
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + FirstName + "]";
	}
	
}