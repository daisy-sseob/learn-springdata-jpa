package me.sseob.learnspringdatajpa.domain.repository;

import me.sseob.learnspringdatajpa.domain.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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

		commentRepository.save(comment);

		List<Comment> comments = commentRepository.findAll();
		assertThat(comments.size()).isEqualTo(1);

		long count = commentRepository.count();
		assertThat(count).isEqualTo(1);
		
		Optional<Comment> commentById = commentRepository.findById(10L);
		assertThat(commentById).isEmpty();
		Comment notEmptyComment = commentById.orElseThrow(IllegalArgumentException::new);
	}
}