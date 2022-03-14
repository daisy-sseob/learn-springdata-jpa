package me.sseob.jpa.practice.sql.jpql;

import me.sseob.jpa.practice.basic.Address;
import me.sseob.jpa.practice.basic.Member;
import me.sseob.jpa.practice.basic.MemberDTO;
import me.sseob.jpa.practice.basic.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

/*
	jpql은 객체지향 sql이다. 테이블이 아닌 객체 중심으로 대상을 검색할 수 있다.
 */
public class JpqlMain {
	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("sseob");
		EntityManager em = entityManagerFactory.createEntityManager(); // entity manager 생성하여 얻음

		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		
		try {

			for (int i = 0; i < 50; i++) {
				Member member = new Member("현섭" + i);
				member.setAge(i);
				em.persist(member);
			}

			em.flush();
			em.clear();
			
			/*
				jpql이 아닌 JPA 스펙과 관련없는 select행위(connection객체를 통해 직접 select 하는 경우와 같은..)를 하는 경우
				persist를 한다고 헤서 바로 database에 insert가 되는게 아니고 commit 시점에 종합적으로 동작하기 때문에 적절한 타이밍에 
				강제로 flush해주는 행위가 필요하다. (실제 insert하고나서 select하도록)
				jpql은 알아서 강제 flush가 발생해서 아래 코드는 정상적으로 insert쿼리가 발생하고 그뒤에 select된다.
			 */

			// use jpql query parameter
			List<Member> findMember = em.createQuery("select m from Member as m where m.name = :name", Member.class)
					.setParameter("name", "sseob")
					.getResultList();
			
			List<Member> findPagingMember = em.createQuery("select m from Member as m order by m.age desc ", Member.class)
					.setFirstResult(1)
					.setMaxResults(10)
					.getResultList();

			System.out.println("====================== order by paging 확인하기 ======================");
			findPagingMember.forEach(System.out::println);
			System.out.println("====================== order by paging 확인하기 ======================");

			// join이 필요한 경우 join을 jpql에 명시하여 작성하자. 묵시적 조인이 가능하지만 유지보수적 관점에서 명시적 조인을 사용하자
			List<Team> findTeam = em.createQuery("select t from Member as m join m.team t", Team.class)
					.getResultList();

			// embedded type 프로젝션
			List<Address> addresses = em.createQuery("select m.homeAddress from Member as m", Address.class)
					.getResultList();

			// 값을 dto에 바로 매핑하기
			List<MemberDTO> findMember2 = em.createQuery("select new me.sseob.jpa.practice.basic.MemberDTO(m.age, m.name) from Member as m", MemberDTO.class)
					.getResultList();
			System.out.println("findMember2.get(0).getName() = " + findMember2.get(0).getName());

			transaction.commit();
		} catch (Exception e){
			transaction.rollback();
		} finally {
			em.close();
		}
		entityManagerFactory.close();
	}
}
