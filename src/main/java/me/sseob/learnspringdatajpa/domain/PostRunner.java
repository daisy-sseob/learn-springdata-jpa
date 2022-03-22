package me.sseob.learnspringdatajpa.domain;

import org.hibernate.Session;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
@Transactional
public class PostRunner implements ApplicationRunner {

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {

		Post post = new Post();
		post.setTitle("포스트 제목");

		Comment comment = new Comment();
		comment.setComment("댓글입니당");
		
		Comment comment2 = new Comment();
		comment2.setComment("두번째 댓글");
		
		post.addComment(comment);
		post.addComment(comment2);
		
		final Session session = entityManager.unwrap(Session.class);
		
		// cascade option을 통해 영속성을 전이하여 comment까지 저장
//		session.save(post);

		// CascadeType Remove. 영속성 전이를 통해 comment까지 삭제
//		final Post findPost = session.get(Post.class, 5L);
//		session.delete(findPost);
	}
}
