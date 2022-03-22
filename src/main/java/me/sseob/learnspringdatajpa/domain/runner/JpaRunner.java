package me.sseob.learnspringdatajpa.domain.runner;

import me.sseob.learnspringdatajpa.domain.Account;
import me.sseob.learnspringdatajpa.domain.Address;
import me.sseob.learnspringdatajpa.domain.Study;
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
		
		Study study = new Study();
		study.setName("spring data jpa - sseob");
		
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
		account.addStudy(study);
		
		final Session session = entityManager.unwrap(Session.class);
		session.save(account);
		session.save(study);

		// 영속성 컨텍스트가 관리하는 객체. 그러니까 persistent 상태의 객체의 데이터를 몇번이나 수정했다고 해서 모두 update query가 발생하지 않는다. 
		final Account sseob = session.load(Account.class, account.getId());
		sseob.setUsername("심현섭");
		sseob.setUsername("심현섭 심현섭");
		sseob.setUsername("심현섭 sseob");
		System.out.println("load.getUsername() = " + sseob.getUsername());
	}
}
