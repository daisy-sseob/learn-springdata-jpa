package me.sseob.learnspringdatajpa.domain.repository;

import me.sseob.learnspringdatajpa.domain.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

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

		createAndSaveComment("sseob 댓글 1", 1000);
		createAndSaveComment("현섭 댓글 2", 10);
		createAndSaveComment("이거 너무 재밌는데요 ? sseob", 11);

		List<Comment> comments = commentRepository.findAll();
		assertThat(comments.size()).isEqualTo(3);

		long count = commentRepository.count();
		assertThat(count).isEqualTo(3);
		
		Optional<Comment> commentById = commentRepository.findById(1L);
		assertThat(commentById).isNotEmpty();
		Comment notEmptyComment = commentById.orElseThrow(IllegalArgumentException::new);

		Page<Comment> byLikeCountGreaterThanAndPost = commentRepository.findByLikeCountGreaterThanAndPost(10, null, PageRequest.of(0, 10));
		assertThat(byLikeCountGreaterThanAndPost.getTotalPages()).isEqualTo(1);

		byLikeCountGreaterThanAndPost.stream().forEach(System.out::println);

		List<Comment> findBycommentComtains = commentRepository.findByCommentContainsIgnoreCaseAndLikeCountGreaterThanEqual("SSEOB", 10);
		findBycommentComtains.forEach(System.out::println);
		assertThat(findBycommentComtains.size()).isEqualTo(2);

		Page<Comment> commentContainsOrderByLikeCount = commentRepository
				.findByCommentContainsOrderByLikeCount("댓글", PageRequest.of(0, 10), Sort.by(Sort.Direction.DESC, "LikeCount"));
		assertThat(commentContainsOrderByLikeCount.getNumberOfElements()).isEqualTo(2);
		assertThat(commentContainsOrderByLikeCount).first().hasFieldOrPropertyWithValue("likeCount", 1000);
		
	}

	private void createAndSaveComment(String s, int i) {
		Comment comment = new Comment();
		comment.setComment(s);
		comment.setLikeCount(i);
		commentRepository.save(comment);
	}
}