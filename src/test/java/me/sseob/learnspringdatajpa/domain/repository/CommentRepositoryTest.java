package me.sseob.learnspringdatajpa.domain.repository;

import me.sseob.learnspringdatajpa.domain.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CommentRepositoryTest {

	@Autowired
	CommentRepository commentRepository;
	
	@Test
	public void crud() {

		Comment comment = new Comment();
		comment.setComment("댓글 1");
		comment.setLikeCount(1000);
		
		Comment comment2 = new Comment();
		comment2.setComment("댓글 2");
		comment2.setLikeCount(10);
		
		commentRepository.save(comment);
		commentRepository.save(comment2);

		List<Comment> comments = commentRepository.findAll();
		assertThat(comments.size()).isEqualTo(2);

		long count = commentRepository.count();
		assertThat(count).isEqualTo(2);
		
		Optional<Comment> commentById = commentRepository.findById(1L);
		assertThat(commentById).isNotEmpty();
		Comment notEmptyComment = commentById.orElseThrow(IllegalArgumentException::new);

		Page<Comment> byLikeCountGreaterThanAndPost = commentRepository.findByLikeCountGreaterThanAndPost(10, null, PageRequest.of(0, 10));
		assertThat(byLikeCountGreaterThanAndPost.getTotalPages()).isEqualTo(1);

		byLikeCountGreaterThanAndPost.stream().forEach(System.out::println);
	}
}