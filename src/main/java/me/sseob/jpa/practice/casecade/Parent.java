package me.sseob.jpa.practice.casecade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Parent {

	@Id
	@GeneratedValue
	@Column(name = "parent_id")
	private Long id;

	private String name;

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true) // 두개의 옵션이 모두 켜져 있으면 부모 엔티티만을 통해 자식 엔티티 생명주기를 할 수 있다.
	private List<Child> childList = new ArrayList<>();
	
	public Parent(String name) {
		this.name = name;
	}

	public Parent() {
		
	}
	
	// 연관관계 편의 method
	public void addChild(Child child) {
		childList.add(child);
		child.setParent(this);
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

	public List<Child> getChildList() {
		return childList;
	}

	public void setChildList(List<Child> childList) {
		this.childList = childList;
	}
}
