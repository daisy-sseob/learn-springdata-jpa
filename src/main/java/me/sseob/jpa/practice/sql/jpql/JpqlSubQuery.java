package me.sseob.jpa.practice.sql.jpql;

import me.sseob.jpa.practice.basic.Member;
import me.sseob.jpa.practice.basic.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpqlSubQuery {
	
	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("sseob");
		EntityManager em = entityManagerFactory.createEntityManager(); // entity manager 생성하여 얻음

		EntityTransaction transaction = em.getTransaction();
		transaction.begin();

		try {

			Team team = new Team("뉴캐슬");
			em.persist(team);

			Member member = new Member("뉴캐슬");
			member.setAge(29);
			member.setTeam(team);
			em.persist(member);
			
			team.addMember(member);

			em.flush();
			em.clear();

			String query = "select (select avg(m1.age) from Member m1) from Member m";
			List<Member> resultList = em.createQuery(query, Member.class)
					.getResultList();
			resultList.forEach(System.out::println);
			
			transaction.commit();
		} catch (Exception e){
			transaction.rollback();
		} finally {
			em.close();
		}
		entityManagerFactory.close();
	}
}
