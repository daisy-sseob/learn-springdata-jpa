package me.sseob.jpa.practice.basic;

import javax.persistence.*;

@Entity
@Table(name = "ADDRESS")
public class AddressEntity {

	@Id @GeneratedValue
	@Column(name = "address_id")
	private Long id;

	@Embedded
	private Address address;

	public AddressEntity() {
	}

	public AddressEntity(Address address) {
		this.address = address;
	}

	public AddressEntity(String city, String street, String zip) {
		this.address = new Address(city, street, zip);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
