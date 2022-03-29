package me.sseob.learnspringdatajpa.domain.repository;

import me.sseob.learnspringdatajpa.domain.Comment;
import me.sseob.learnspringdatajpa.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

@RepositoryDefinition(domainClass = Comment.class, idClass = Long.class) // JpaRepository 상속받지 않고 직접 정의하기.
public interface CommentRepository extends BaseRepository<Comment, Long> {

	List<Comment> findByCommentContains(String keyword);

	Page<Comment> findByLikeCountGreaterThanAndPost(int likeCount, Post post, Pageable pageable);
}
