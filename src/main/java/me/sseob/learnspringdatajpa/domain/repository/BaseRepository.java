package me.sseob.learnspringdatajpa.domain.repository;

import me.sseob.learnspringdatajpa.domain.Comment;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T, Id extends Serializable> extends Repository<T, Id> {
	
	<E extends T> E save(@NonNull E entity);

	List<T> findAll();

	long count();

	<E extends T> Optional<E> findById(Id id);
}
