package me.sseob.jpa.practice.casecade;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class CasecadeMain {
	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("sseob");
		EntityManager entityManager = entityManagerFactory.createEntityManager(); // entity manager 생성하여 얻음

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		try {

			Child child = new Child("child");
			Child child2 = new Child("child2");
			Child child3 = new Child("child3");
			
			Parent parent = new Parent("parent");
			parent.addChild(child);
			parent.addChild(child2);
			parent.addChild(child3);

			entityManager.persist(parent);

			entityManager.flush();
			entityManager.clear();
			
			Parent findParent = entityManager.find(Parent.class, parent.getId());
			
			/*
				orphanRemoval option을 통해 고아 객체가 삭제되는걸 확인할 수 있다.
				parent를 삭제하더라도 child객체들이 고아가 되기 때문에 Casecade Remove option 처럼 동작한다.
			 */
			findParent.getChildList().remove(0);

			transaction.commit();
		} catch (Exception e){
			transaction.rollback();
			e.printStackTrace();
		} finally {
			entityManager.close();
		}
		entityManagerFactory.close();
	}
}
