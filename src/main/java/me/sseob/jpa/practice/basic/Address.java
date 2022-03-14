package me.sseob.jpa.practice.basic;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Address {
	
	@Column(length = 20)
	private String city;
	@Column(length = 100)
	private String street;
	@Column(length = 8)
	private String zipcode;

	public Address() {
	}

	public Address(String city, String street, String zipcode) {
		this.city = city;
		this.street = street;
		this.zipcode = zipcode;
	}

	public String getCity() {
		return city;
	}

	public String getStreet() {
		return street;
	}
	
	public String getZipcode() {
		return zipcode;
	}
	
	private String fullAddress() {
		return getCity() + " " + getStreet() + " " + getZipcode();
	}

	// equals와 hashCode method를 통해 값 타입의 비교를 지원한다. 객체를 비교하는게 아니라 값을 비교하도록 설계한다.
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Address address = (Address) o;
		return Objects.equals(getCity(), address.getCity()) && 
				Objects.equals(getStreet(), address.getStreet()) &&
				Objects.equals(getZipcode(), address.getZipcode());
	}

	@Override
	public int hashCode() {
		return Objects.hash(city, street, zipcode);
	}

	@Override
	public String toString() {
		return "Address{" +
				"city='" + city + '\'' +
				", street='" + street + '\'' +
				", zipcode='" + zipcode + '\'' +
				'}';
	}
}
