package me.sseob.learnspringdatajpa.domain.runner;

import me.sseob.learnspringdatajpa.domain.Comment;
import me.sseob.learnspringdatajpa.domain.Post;
import org.hibernate.Session;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//@Component
@Transactional
public class FetchTestRunner implements ApplicationRunner {

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {

		final Session session = entityManager.unwrap(Session.class);
		
		// fetch type별 select query문 체크
		final Post findPost = session.get(Post.class, 14L);
		findPost.getComments().forEach(comment -> System.out.println("comment = " + comment.getComment()));
	}
}
