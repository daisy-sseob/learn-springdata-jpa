package me.sseob.jpa.practice.basic;

import javax.persistence.*;

@Entity
public class Locker extends BaseEntity{

	@Id @GeneratedValue
	@Column(name = "locker_id")
	private Long id;

	private String name;

	@OneToOne(mappedBy = "locker")
	private Member member;
}
