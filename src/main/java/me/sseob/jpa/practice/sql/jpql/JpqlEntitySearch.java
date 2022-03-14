package me.sseob.jpa.practice.sql.jpql;

import me.sseob.jpa.practice.basic.Member;
import me.sseob.jpa.practice.basic.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpqlEntitySearch {
	
	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("sseob");
		EntityManager em = entityManagerFactory.createEntityManager(); // entity manager 생성하여 얻음

		EntityTransaction transaction = em.getTransaction();
		transaction.begin();

		try {

			Team team = new Team("뉴캐슬");
			em.persist(team);

			Member member = new Member("현섭");
			member.setAge(29);
			member.setTeam(team);
			em.persist(member);
			
			Member sseob = new Member("sseob");
			sseob.setAge(29);
			sseob.setTeam(team);
			em.persist(sseob);
			
			Member orolsyeo = new Member("orolsyeo");
			orolsyeo.setAge(29);
			orolsyeo.setTeam(team);
			em.persist(orolsyeo);

			// 벌크 연산.
			int resultcount = em.createQuery("update Member m set m.age = 20").executeUpdate();
			System.out.println("resultcount = " + resultcount);

			// 영속성 컨텍스트 초기화 하기전
			System.out.println("member.getAge() = " + member.getAge());
			System.out.println("member.getAge() = " + sseob.getAge());
			System.out.println("member.getAge() = " + orolsyeo.getAge());
			Member member1 = em.find(Member.class, member.getId());
			System.out.println("member1.getAge() = " + member1.getAge());
			
			// 영속성 컨텍스트 초기화 후
			em.clear();
			Member initMember = em.find(Member.class, member.getId());
			System.out.println("member1.getAge() = " + initMember.getAge());
			
			// entity로 직접 조회해도 식별자인 id를 이용해 조회를 하게 된다.
			List<Member> resultList = em.createQuery("select m from Member as m where m = :member", Member.class)
					.setParameter("member", member)
					.getResultList();
			resultList.forEach(System.out::println);

			// 외래키인 Team객체로도 조회할 수 있다.
			List<Member> resultList2 = em.createQuery("select m from Member as m where m.team = :team", Member.class)
					.setParameter("team", team)
					.getResultList();
			resultList2.forEach(System.out::println);

			List<Member> resultList3 = em.createNamedQuery("Member.findByUsername", Member.class)
					.setParameter("name", "sseob")
					.getResultList();
			resultList3.forEach(System.out::println);


			transaction.commit();
		} catch (Exception e){
			e.printStackTrace();
			transaction.rollback();
		} finally {
			em.close();
		}
		entityManagerFactory.close();
	}
}
