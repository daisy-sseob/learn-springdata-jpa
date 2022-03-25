package me.sseob.learnspringdatajpa.domain.runner;

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
		postRepository.findAll().forEach(System.out::println);
	}
}
