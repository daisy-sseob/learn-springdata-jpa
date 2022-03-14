package me.sseob.jpa.practice.sql.jpql;

import me.sseob.jpa.practice.basic.Member;
import me.sseob.jpa.practice.basic.MemberType;
import me.sseob.jpa.practice.basic.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Collection;
import java.util.List;

/*
	jpql은 객체지향 sql이다. 테이블이 아닌 객체 중심으로 대상을 검색할 수 있다.
 */
public class JpqlRouteExpression {
	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("sseob");
		EntityManager em = entityManagerFactory.createEntityManager(); // entity manager 생성하여 얻음

		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		
		try {

			Team team = new Team("뉴캐슬");
			em.persist(team);
			
			for (int i = 0; i < 10; i++) {
				Member member = new Member("현섭" + i);
				member.setAge(i * 10);
				member.setMemberType(MemberType.ADMIN);
				member.setTeam(team);
				em.persist(member);
			}

			em.flush();
			em.clear();

			String query = "select m.team.name from Member m"; // 단일값 연관 경로는 inner join이 발생하며 단일값 연관관계에 의해 추가 탐색이 가능하다.
			List<String> resultList = em.createQuery(query, String.class).getResultList();
			for (String str : resultList) {
				System.out.println("result = " + str);
			}
			
			String collectionRouteQuery = "select t.members from Team t"; // 컬렉션 연관 경로 또한 inner join이 발생하지만 추가 탐색이 불가능하다. (묵시적 조인)
			Collection resultList2 = em.createQuery(collectionRouteQuery, Collection.class).getResultList();
			System.out.println("resultList2 = " + resultList2);
	
			String joinQuery = "select m.name from Team t join t.members m"; // 튜닝도 예측도 어려운 묵시적 조인을 사용하지 말고 명시적 조인을 사용하여 객체 탐색과 유지보수 지향적 Jpql을 짜자
			List<String> resultList3 = em.createQuery(joinQuery, String.class).getResultList();
			System.out.println("resultList3 = " + resultList3);
			
			transaction.commit();
		} catch (Exception e){
			transaction.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
		entityManagerFactory.close();
	}
}
