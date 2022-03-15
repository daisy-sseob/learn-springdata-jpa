package me.sseob.learnspringdatajpa.domain;

import org.hibernate.Session;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class JpaRunner implements ApplicationRunner{

	@PersistenceContext
	EntityManager entityManager;

	@Transactional
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		Account account = new Account();
		account.setUsername("sseob");
		account.setPassword("1234");
		account.setHomeAddress(Address.builder()
				.city("고양시")
				.zipCode("5554")
				.street("도래울 1로")
				.build());
		
		account.setOfficeAddress(Address.builder()
				.city("서울 특별시")
				.zipCode("533524")
				.street("가산디지털 1로")
				.build());

		final Session session = entityManager.unwrap(Session.class);
		session.save(account);
	}
}
