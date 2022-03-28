package me.sseob.learnspringdatajpa.domain.repository;

import me.sseob.learnspringdatajpa.domain.Comment;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Comment.class, idClass = Long.class) // JpaRepository 상속받지 않고 직접 정의하기.
public interface CommentRepository extends BaseRepository<Comment, Long> {

}
