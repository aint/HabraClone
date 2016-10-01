package com.github.aint.habraclone.data.repository;

import com.github.aint.habraclone.data.model.IEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface GenericRepository<T extends IEntity, ID extends Serializable> extends CrudRepository<T, ID> {

    List<T> findByOrderByRatingDesc();

}
