package me.sseob.jpa.practice.basic;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;


/*
	MappedSuperClass로는 아무리 어떠한 클래스의 부모타입이더라도 EntityManager를 통해 조회, 검색이 불가능하다.
	어차피 JPA를 통해 Table로 매핑하여 사용할게 아니기 때문에 추상 클래스로 만들어서 사용한다.
 */
@MappedSuperclass // mapping 정보만 받는 super class임을 선언한다.
public abstract class BaseEntity {

	private String createdBy;
	private LocalDateTime createdDate;
	private String lastModifiedBy;
	private LocalDateTime lastModifiedDate;

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public LocalDateTime getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
}
