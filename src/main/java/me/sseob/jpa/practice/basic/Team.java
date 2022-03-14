package me.sseob.jpa.practice.basic;


import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Team extends BaseEntity{

	@Id
	@GeneratedValue
	@Column(name = "team_id")
	private Long id;
	private String name;
	
	// 읽기전용의 매핑인 것이다. 연관관계의 주인이 아님.
//	@BatchSize(size = 100)
	@OneToMany(mappedBy = "team")
	private Set<Member> members = new HashSet<>();
	
	// 연관관계 util method
	public void addMember(Member member) {
		member.setTeam(this);
		this.members.add(member);
	}

	public Team(String name) {
		this.name = name;
	}

	public Team() {

	}

	public Set<Member> getMembers() {
		return members;
	}

	public void setMembers(Set<Member> members) {
		this.members = members;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Team{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}
