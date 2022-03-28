package me.sseob.learnspringdatajpa.domain.repository;

import me.sseob.learnspringdatajpa.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

	Page<Post> findByTitleContains(String title, Pageable pageable);
}
