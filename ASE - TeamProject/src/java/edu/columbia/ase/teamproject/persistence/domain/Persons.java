package edu.columbia.ase.teamproject.persistence.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Persons")
public class Persons {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	
	private String lastName;
	private String firstName;
	private String address;
	private String city;
	private String email;
	
	public Persons() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Persons(String lastName, String firstName, String address,
			String city, String email) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.address = address;
		this.city = city;
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	@Override
	public String toString() {
		return "Persons [id=" + id + ", lastName=" + lastName + ", firstName="
				+ firstName + ", address=" + address + ", city=" + city
				+ ", email=" + email + "]";
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Persons)) {
			return false;
		}
		Persons other = (Persons) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}
	
	
	
	
}
