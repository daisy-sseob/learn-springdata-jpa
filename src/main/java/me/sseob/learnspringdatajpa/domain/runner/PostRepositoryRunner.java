package me.sseob.learnspringdatajpa.domain.runner;

import me.sseob.learnspringdatajpa.domain.Comment;
import me.sseob.learnspringdatajpa.domain.Post;
import me.sseob.learnspringdatajpa.domain.repository.PostRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class PostRepositoryRunner implements ApplicationRunner {

	final PostRepository postRepository;

	public PostRepositoryRunner(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		Post post = new Post();
		post.setTitle("spring data jpa hibernate parameter보기");

		Comment comment = new Comment();
		comment.setComment("어떻게 보나요 ?");

		post.addComment(comment);
		
		postRepository.save(post);
	}
}
