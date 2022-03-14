package me.sseob.jpa.practice.basic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Set;

public class ImportantExample {

	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("sseob");
		EntityManager entityManager = entityManagerFactory.createEntityManager(); // entity manager 생성하여 얻음

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		try {
			
			/*
			    양방향 매핑시에 연관관계의 소유주에게 데이터가 입력되도록 코드를 짜야된다.
			    양방향 관계이면 객체관점에서 양쪽에 모두 값을 설정하자.
			 */

			Team newcastle = new Team("Newcastle");
			entityManager.persist(newcastle);
			
			Member member = new Member("심현섭");
			entityManager.persist(member);

			newcastle.addMember(member);

			entityManager.flush();
			entityManager.close();

			Team findTeam = entityManager.find(Team.class, newcastle.getId());
			Set<Member> members = findTeam.getMembers();

			System.out.println("======================");
			for (Member m : members) {
				System.out.println("member1.getName() = " + m.getName());
			}
			System.out.println("======================");

			transaction.commit();
		} catch (Exception e){
			transaction.rollback();
		} finally {
			entityManager.close();
		}
		entityManagerFactory.close();
	}
}
