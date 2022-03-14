package me.sseob.jpa.practice.sql.criteria;

import me.sseob.jpa.practice.basic.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/*
	JPA 표준 스펙으로 제공하지만 어느정도 복잡성이 있기 때문에 실무에서는 보통 쓰지 않는다.
	Criteria는 JPQL Builder역할을 한다.
 */
public class CriteriaMain {
	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("sseob");
		EntityManager em = entityManagerFactory.createEntityManager(); // entity manager 생성하여 얻음

		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		
		try {

			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Member> query = cb.createQuery(Member.class);
			Root<Member> member = query.from(Member.class);
			CriteriaQuery<Member> cq = query
					.select(member)
					.where(cb.equal(member.get("name"), "현섭"));

			List<Member> resultList = em.createQuery(cq).getResultList();
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
