package me.sseob.jpa.practice.basic.main;

import me.sseob.jpa.practice.basic.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class BasicMain {
	
	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("sseob");
		EntityManager entityManager = entityManagerFactory.createEntityManager(); // entity manager 생성하여 얻음

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		try {

			Team team = new Team("team !");
			entityManager.persist(team);
			
			// 임베디드 타입을 공유하여 사용할 경우 side effect가 심하게 발생할 수 있다. member의 address만 변경했는데 다른 member도 같이 update 되는 상황이 발생함.
			Address address = new Address("seoul", "홍대", "123123");

			Member member = new Member("sseob");
			member.setHomeAddress(address);
			member.getAddressHistory().add(new AddressEntity("고양시", "도래울 1로", "123123"));
			member.getAddressHistory().add(new AddressEntity("고양시", "도래울 2로", "123123123123"));
			member.getAddressHistory().add(new AddressEntity("고양시", "도래울 3로", "1231244444443"));
			member.setWorkAddress(new Address("goyang", "도래울 1로", "8020"));
			member.setWorkPeriod(new Period(LocalDateTime.now(), LocalDateTime.now().minusDays(20)));
			member.setCreatedBy("심현섭");
			member.setCreatedDate(LocalDateTime.now());
			member.setTeam(team);
			entityManager.persist(member);

			Member member2 = new Member("sseob2");
			member2.setHomeAddress(address);
			member2.setWorkAddress(new Address("goyang", "도래울 1로", "8020"));
			member2.setWorkPeriod(new Period(LocalDateTime.now(), LocalDateTime.now().minusDays(20)));
			member2.setCreatedBy("심현섭2");
			member2.setCreatedDate(LocalDateTime.now());
			member2.setTeam(team);
			entityManager.persist(member2);

			// setter를 없애서 불변 객체로 만들면 임베디드 타입의 객체 참조 공유 문제를 방지할 수 있다.
//			member2.getHomeAddress().setCity("서울 특별시");
			
			// address를 변경하고 싶다면 새로운 객체를 만들어 변경하자.
			Address copyAddress = new Address(address.getCity(), "도래울입니당.", address.getZipcode());
			member.setHomeAddress(copyAddress);
			
			entityManager.flush();
			entityManager.clear();

			Member findMember = entityManager.find(Member.class, member.getId());
			System.out.println("member1 = " + findMember.getName()); // Lock, Team을 지연로딩으로 설정후 조회하지 않으면 Member만 조회하는 Select query실행.
			System.out.println("member1.getTeam() = " + findMember.getTeam().getClass()); // team hibernate proxy객체가 출력된다. 즉 지연로딩으로 설정하여 proxy객체를 참조하게 만든다. proxy객체 출력후 select query가 실행되는걸 볼 수 있음.
			System.out.println("member1.getTeam() = " + findMember.getTeam().getName()); // 지연 로딩에 의해 나중에 Team이 join된다. 실제 사용될 때 proxy instance 초기화.
			findMember.getAddressHistory().forEach(System.out::println);
			Address homeAddress = findMember.getHomeAddress();
			
			// 값 type 업데이트
			findMember.setHomeAddress(new Address(homeAddress.getCity(),homeAddress.getStreet(),"999999"));
			// 값 type collection 데이터 변경
			findMember.getAddressHistory().remove( new AddressEntity("고양시", "도래울 1로", "123123"));
			findMember.getAddressHistory().add(new AddressEntity("새로운 고양시", "도래울 1로", "11111"));
			
			// 반대로 FetchType.EAGER 즉시로딩을 설정하게 되면 애초에 member, team을 join하여 select한다. 프록시 객체를 사용하지 않아도 된다.

			// 즉시로딩의 N + 1 문제는 지연로딩 + fetch join으로 해결할 수 있다.
			List<Member> resultList = entityManager.createQuery("select m from Member m join fetch m.team", Member.class).getResultList();
			resultList.forEach(System.out::println);

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
