package me.sseob.learnspringdatajpa.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {

	@Id
	@GeneratedValue
	private Long id;

	private String username;

	private String password;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created = new Date();

	private String yes;

	@Transient
	private String no;

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "street", column = @Column(name = "home_street")),
			@AttributeOverride(name = "city", column = @Column(name = "home_city")),
			@AttributeOverride(name = "zipCode", column = @Column(name = "home_zipCode")),
			@AttributeOverride(name = "state", column = @Column(name = "home_state"))
	})
	private Address homeAddress;

	@Embedded
	private Address officeAddress;

	@OneToMany(mappedBy = "owner")
	private Set<Study> studies = new HashSet<>();

	public Set<Study> getStudies() {
		return studies;
	}

	public void setStudies(Set<Study> studies) {
		this.studies = studies;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getYes() {
		return yes;
	}

	public void setYes(String yes) {
		this.yes = yes;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Address getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(Address homeAddress) {
		this.homeAddress = homeAddress;
	}

	public Address getOfficeAddress() {
		return officeAddress;
	}

	public void setOfficeAddress(Address officeAddress) {
		this.officeAddress = officeAddress;
	}

	public void addStudy(Study study) {
		study.setOwner(this);
		this.getStudies().add(study);
	}
	
	public void removeStudy(Study study) {
		study.setOwner(null); // this 정보 삭제
		this.getStudies().remove(study);
	}
}
