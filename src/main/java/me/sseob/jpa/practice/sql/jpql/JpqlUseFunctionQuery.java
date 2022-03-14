package me.sseob.jpa.practice.sql.jpql;

import me.sseob.jpa.practice.basic.Member;
import me.sseob.jpa.practice.basic.MemberType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

/*
	jpql은 객체지향 sql이다. 테이블이 아닌 객체 중심으로 대상을 검색할 수 있다.
 */
public class JpqlUseFunctionQuery {
	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("sseob");
		EntityManager em = entityManagerFactory.createEntityManager(); // entity manager 생성하여 얻음

		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		
		try {

			for (int i = 0; i < 10; i++) {
				Member member = new Member("현섭" + i);
				member.setAge(i * 10);
				member.setMemberType(MemberType.ADMIN);
				em.persist(member);
			}

			em.flush();
			em.clear();

			// JPQL의 case문
			String query = "select " +
									"case " +
									"       when m.age <= 10 then '초등학생' " +
									"       when m.age >= 50 then '무료' " +
									"       else '일반'" +
									"end as buspay " +
							"from Member m";
			List<String> result = em.createQuery(query, String.class).getResultList();
			for (String str : result) {
				System.out.println("str = " + str);
			}
			
			// JPQL의 function
			String functionQuery = "select substring(m.name,0,1) from Member m";
			List<String> result2 = em.createQuery(functionQuery, String.class).getResultList();
			for (String resultStr : result2) {
				System.out.println("result = " + resultStr);
			}
			
			String function = "select function('group_concat', m.name) from Member m";
			List<String> functionResult = em.createQuery(function, String.class).getResultList();
			for (String str : functionResult) {
				System.out.println("function result = " + str);
			}

			transaction.commit();
		} catch (Exception e){
			transaction.rollback();
		} finally {
			em.close();
		}
		entityManagerFactory.close();
	}
}
