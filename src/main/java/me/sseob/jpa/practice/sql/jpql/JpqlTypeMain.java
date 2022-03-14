package me.sseob.jpa.practice.sql.jpql;

import me.sseob.jpa.practice.basic.Member;
import me.sseob.jpa.practice.basic.MemberType;
import me.sseob.jpa.practice.item.Book;
import me.sseob.jpa.practice.item.Item;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

/*
	jpql은 객체지향 sql이다. 테이블이 아닌 객체 중심으로 대상을 검색할 수 있다.
 */
public class JpqlTypeMain {
	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("sseob");
		EntityManager em = entityManagerFactory.createEntityManager(); // entity manager 생성하여 얻음

		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		
		try {

			for (int i = 0; i < 5; i++) {
				Member member = new Member("현섭" + i);
				member.setAge(i);
				member.setMemberType(MemberType.ADMIN);
				em.persist(member);

				Book book = new Book();
				book.setName("현섭쿠의 책" + i);
				book.setAuthor("심현섭");
				em.persist(book);
			}

			em.flush();
			em.clear();

			// JPQL의 Type 표현
			String query = "select m.name, 'HEELO', true from Member m " +
							"where m.memberType = :memberType";
			List<Object[]> result = em.createQuery(query)
					.setParameter("memberType",MemberType.ADMIN)
					.getResultList();
			
			for (Object[] objects : result) {
				System.out.println("objects[0] = " + objects[0]);
				System.out.println("objects[1] = " + objects[1]);
				System.out.println("objects[2] = " + objects[2] + "\n");
			}

			List<Item> books = em.createQuery("select i from Item i " +
							"                       where   type(i) = Book " +
													"       and i.id between 0 and 2", Item.class)
					.getResultList();
			books.forEach(System.out::println);

			transaction.commit();
		} catch (Exception e){
			transaction.rollback();
		} finally {
			em.close();
		}
		entityManagerFactory.close();
	}
}
