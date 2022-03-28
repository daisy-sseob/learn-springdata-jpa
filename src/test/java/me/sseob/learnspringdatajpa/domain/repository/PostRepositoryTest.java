package me.sseob.learnspringdatajpa.domain.repository;

import me.sseob.learnspringdatajpa.domain.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PostRepositoryTest {

	@Autowired
	PostRepository postRepository;
	
	@Test
	@Rollback(false) // @DataJpaTest annotation은 @Transactional annotation이 포함 되어있기 때문에 기본 세팅으로는 test코드에서 query를 확인할 수 없다.
	public void crudRepository() {
		Post post = new Post();
		post.setTitle("테스트 post");
		assertThat(post.getId()).isNull();
		
		Post savePost = postRepository.save(post);
		assertThat(savePost.getId()).isNotNull();

		List<Post> posts = postRepository.findAll();
		assertThat(posts.size()).isEqualTo(1);
		assertThat(posts).contains(savePost);

		Page<Post> page = postRepository.findAll(PageRequest.of(0, 10));
		assertThat(page.getTotalElements()).isEqualTo(1);
		assertThat(page.getNumber()).isEqualTo(0); // 현재 page number
		assertThat(page.getNumberOfElements()).isEqualTo(1);
		assertThat(page.getSize()).isEqualTo(10); // 요청한 page size

		Page<Post> pageContains = postRepository.findByTitleContains("테스트", PageRequest.of(0, 10));
		assertThat(pageContains.getTotalElements()).isEqualTo(1);
		assertThat(pageContains.getNumber()).isEqualTo(0);
		assertThat(pageContains.getNumberOfElements()).isEqualTo(1);
		assertThat(pageContains.getSize()).isEqualTo(10);

		long test = postRepository.countByTitleContains("test");
		assertThat(test).isEqualTo(0);
		
	}
}