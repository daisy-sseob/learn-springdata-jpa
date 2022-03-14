package me.sseob.jpa.practice.sql.jpql;

import me.sseob.jpa.practice.basic.Member;
import me.sseob.jpa.practice.basic.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpqlJoinMain {
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

			List<Member> innerJoinList = em.createQuery("select m from Member as m inner join m.team as t", Member.class).getResultList();
			innerJoinList.forEach(System.out::println);

			List<Member> leftJoinList = em.createQuery("select m from Member as m left join m.team as t", Member.class).getResultList();
			leftJoinList.forEach(System.out::println);
			
			// on절도 사용 가능하다.
			List<Member> joinList = em.createQuery("select m from Member as m left join Team t on m.name = t.name", Member.class).getResultList();
			joinList.forEach(System.out::println);

			List<Member> crossJoinList = em.createQuery("select m from Member as m, Team as t where m.name = t.name", Member.class).getResultList();
			crossJoinList.forEach(System.out::println);
			
			transaction.commit();
		} catch (Exception e){
			transaction.rollback();
		} finally {
			em.close();
		}
		entityManagerFactory.close();
	}
}
